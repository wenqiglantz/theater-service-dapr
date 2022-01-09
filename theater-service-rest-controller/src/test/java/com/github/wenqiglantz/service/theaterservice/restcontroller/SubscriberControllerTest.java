package com.github.wenqiglantz.service.theaterservice.restcontroller;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dapr.client.domain.CloudEvent;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "customer_service", port = "9500", providerType = ProviderType.ASYNCH) //TODO: change to dii provider
@SpringBootTest
public class SubscriberControllerTest {

    @Autowired
    private SubscriberController subscriberController;

    private static final String TOPIC = "dii.integration.customerservice.theaterv1";
    private static final String PUBSUBNAME = "dii-integration";
    private static final String ROUTE = "/dii.integration.customerservice.theaterv1";
    private static final String JSON = "application/json";
    private static final String CLOUD_EVENT_TYPE_SENT = "com.dapr.event.sent";
    private static final String CLOUD_EVENT_SPEC_VERSION = "1.0";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Pact(consumer = "theater_service_consumer")
    public MessagePact validTheaterCreatedEventFromProvider(MessagePactBuilder builder) {

        final Map<String, Object> expectedMetadata = new HashMap<>();
        expectedMetadata.put("topic", TOPIC);
        expectedMetadata.put("pubsubName", PUBSUBNAME);
        expectedMetadata.put("route", ROUTE);
        expectedMetadata.put("Content-Type", JSON);

        return builder
                .hasPactWith("customer_service") //TODO
                //.given("A new theater has been created") //provider state
                .expectsToReceive("valid TheaterCreatedEvent from provider") //request description
                .withContent(LambdaDsl.newJsonBody((object) -> {
                    object.stringValue("theaterId", "595eed0c-eff5-4278-90ad-b952f18dbee8");
                }).build())
                .withMetadata(expectedMetadata)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "validTheaterCreatedEventFromProvider")
    public void testTheaterCreatedEventFromProvider(MessagePact messagePact) throws Exception {
        //get data from messagePact
        String data = messagePact.getMessages().get(0).getContents().valueAsString();

        //convert data json string into LinkedHashMap as Dapr CloudEvent stores data in LinkedHashMap format, https://github.com/dapr/java-sdk/issues/477
        LinkedHashMap<String, String> map = OBJECT_MAPPER.readValue(data, LinkedHashMap.class);

        //construct a Dapr CloudEvent based on messagePact, to be passed to SubscriberController to be consumed
        CloudEvent cloudEvent = new CloudEvent();
        cloudEvent.setId(UUID.randomUUID().toString());
        cloudEvent.setType(CLOUD_EVENT_TYPE_SENT);
        cloudEvent.setSpecversion(CLOUD_EVENT_SPEC_VERSION);
        cloudEvent.setDatacontenttype(JSON);
        cloudEvent.setData(map);

        subscriberController.consumeEvent(cloudEvent);
    }
}
