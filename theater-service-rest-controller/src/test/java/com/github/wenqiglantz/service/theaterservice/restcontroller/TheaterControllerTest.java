package com.github.wenqiglantz.service.theaterservice.restcontroller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.github.wenqiglantz.service.theaterservice.data.TheaterInfo;
import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import com.github.wenqiglantz.service.theaterservice.service.TheaterService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class TheaterControllerTest {
    private static final String THEATER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";
    private static final String CREATOR_SUBJECT_ID = "CREATOR_SUBJECT_ID";
    private static final String CREATOR_ISSUER_ID = "CREATOR_ISSUER_ID";
    private static final List<TheaterInfo> THEATER_INFO_LIST = new ArrayList<>();

    private static final TheaterInfo API_THEATER = TheaterInfo.builder()
            .customSiteCode(CUSTOM_SITE_CODE)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .build();

    private static final String PATH = "/theaters/{theaterId}";
    private static final UriComponentsBuilder URI_COMPONENTS_BUILDER = UriComponentsBuilder.fromPath(PATH);

    private UriComponentsBuilder uriBuilder;

    private TheaterService theaterService;

    @RegisterExtension
    public Mockery context = new JUnit5Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @BeforeEach
    public void setUp() {
        uriBuilder = context.mock(UriComponentsBuilder.class);
        theaterService = context.mock(TheaterService.class);
    }

    @Test
    public void createTheater() {
        context.checking(new Expectations() {
            {
                oneOf(theaterService).saveTheater(API_THEATER, TheaterStatus.PENDING);
                will(returnValue(API_THEATER));
                oneOf(uriBuilder).path(PATH);
                will(returnValue(URI_COMPONENTS_BUILDER));
            }
        });

        TheaterController theaterController = new TheaterController(theaterService);

        ResponseEntity responseEntity = theaterController.createTheater(API_THEATER, uriBuilder);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
    }

    @Test
    public void getTheater() {
        context.checking(new Expectations(){
            {
                oneOf(theaterService).getTheater(THEATER_ID);
                will(returnValue(API_THEATER));
            }
        });

        TheaterController theaterController = new TheaterController(theaterService);

        ResponseEntity responseEntity = theaterController.getTheater(THEATER_ID);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getBody(), is(sameInstance(API_THEATER)));
    }

    @Test
    public void getTheaterHistory() {
        THEATER_INFO_LIST.add(API_THEATER);
        context.checking(new Expectations(){
            {
                oneOf(theaterService).getTheaterHistory(THEATER_ID);
                will(returnValue(THEATER_INFO_LIST));
            }
        });

        TheaterController theaterController = new TheaterController(theaterService);

        ResponseEntity responseEntity = theaterController.getTheaterHistory(THEATER_ID);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getBody(), is(sameInstance(THEATER_INFO_LIST)));
    }
    
}
