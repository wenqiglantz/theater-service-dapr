package com.github.wenqiglantz.service.theaterservice.data.event;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class TheaterCreatedEventTest {

    private static final String THEATER_ID = "ABCDEFG12345678910HIJKLMNOP12345";

    @Test
    public void objectCreation() {
        TheaterCreatedEvent object = TheaterCreatedEvent.builder().theaterId(THEATER_ID).build();
        assertThat(object.getTheaterId(), is(sameInstance(THEATER_ID)));
    }
}
