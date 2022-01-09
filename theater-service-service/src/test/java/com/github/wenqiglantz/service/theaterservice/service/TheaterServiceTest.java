package com.github.wenqiglantz.service.theaterservice.service;

import com.github.wenqiglantz.service.theaterservice.data.TheaterInfo;
import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import com.github.wenqiglantz.service.theaterservice.data.event.TheaterCreatedEvent;
import com.github.wenqiglantz.service.theaterservice.data.exception.NotFoundException;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.CustomSite;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.Theater;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.TheaterHistory;
import com.github.wenqiglantz.service.theaterservice.persistence.repository.CustomSiteRepository;
import com.github.wenqiglantz.service.theaterservice.persistence.repository.TheaterHistoryRepository;
import com.github.wenqiglantz.service.theaterservice.persistence.repository.TheaterRepository;
import com.github.wenqiglantz.service.theaterservice.service.impl.TheaterServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TheaterServiceTest {
    private static final String THEATER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";
    private static final String CREATOR_SUBJECT_ID = "CREATOR_SUBJECT_ID";
    private static final String CREATOR_ISSUER_ID = "CREATOR_ISSUER_ID";
    private static final List<TheaterHistory> THEATER_HISTORIES = new ArrayList<>();
    private static final String CUSTOM_SITE_ID = UUID.randomUUID().toString();

    private static final TheaterInfo API_THEATER = TheaterInfo.builder()
            .customSiteId(CUSTOM_SITE_ID)
            .customSiteCode(CUSTOM_SITE_CODE)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .theaterId(THEATER_ID)
            .build();

    private static final TheaterInfo API_THEATER_CUSTOM_SITE_NULL_ID = TheaterInfo.builder()
            .customSiteCode(CUSTOM_SITE_CODE)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .build();

    private static final TheaterInfo API_THEATER_NULL_ID = TheaterInfo.builder()
            .customSiteId(CUSTOM_SITE_ID)
            .customSiteCode(CUSTOM_SITE_CODE)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .build();

    private static final CustomSite CUSTOM_SITE = CustomSite.builder()
            .customSiteCode(CUSTOM_SITE_CODE)
            .customSiteId(CUSTOM_SITE_ID)
            .build();

    private static final Theater THEATER = Theater.builder()
            .customSite(CUSTOM_SITE)
            .theaterId(THEATER_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .status(TheaterStatus.PENDING)
            .build();

    private static final Theater THEATER_PENDING = Theater.builder()
            .customSite(CUSTOM_SITE)
            .theaterId(THEATER_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .status(TheaterStatus.PENDING)
            .build();

    private static final Theater THEATER_CREATED = Theater.builder()
            .customSite(CUSTOM_SITE)
            .theaterId(THEATER_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .status(TheaterStatus.CREATED)
            .build();

    private static final TheaterHistory THEATER_HISTORY = TheaterHistory.builder()
            .theater(THEATER)
            .status(TheaterStatus.CREATED)
            .build();

    private static final TheaterHistory THEATER_HISTORY_CREATED = TheaterHistory.builder()
            .theater(THEATER_CREATED)
            .status(TheaterStatus.CREATED)
            .build();

    private static final TheaterCreatedEvent THEATER_CREATED_EVENT = TheaterCreatedEvent.builder()
            .theaterId(THEATER_ID)
            .build();

    private TheaterRepository theaterRepository;

    private CustomSiteRepository customSiteRepository;

    private TheaterHistoryRepository theaterHistoryRepository;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        theaterRepository = context.mock(TheaterRepository.class);
        customSiteRepository = context.mock(CustomSiteRepository.class);
        theaterHistoryRepository = context.mock(TheaterHistoryRepository.class);
    }

    @Test
    public void saveTheater() {
        context.checking(new Expectations() {
            {
                oneOf(customSiteRepository).save(CUSTOM_SITE);
                oneOf(theaterRepository).save(THEATER_CREATED);
                oneOf(theaterHistoryRepository).save(THEATER_HISTORY_CREATED);
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        theaterService.saveTheater(API_THEATER, TheaterStatus.CREATED);
    }

    @Test
    public void saveTheaterWithCustomSiteIdNull() {
        context.checking(new Expectations() {
            {
                oneOf(customSiteRepository).save(with(any(CustomSite.class)));
                oneOf(theaterRepository).save(with(any(Theater.class)));
                oneOf(theaterHistoryRepository).save(with(any(TheaterHistory.class)));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        theaterService.saveTheater(API_THEATER_CUSTOM_SITE_NULL_ID, TheaterStatus.CREATED);
    }

    @Test
    public void saveTheaterWithTheaterIdNull() {
        context.checking(new Expectations() {
            {
                oneOf(customSiteRepository).save(with(any(CustomSite.class)));
                oneOf(theaterRepository).save(with(any(Theater.class)));
                oneOf(theaterHistoryRepository).save(with(any(TheaterHistory.class)));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        theaterService.saveTheater(API_THEATER_NULL_ID, TheaterStatus.CREATED);
    }

    @Test
    public void getTheater() {
        context.checking(new Expectations() {
            {
                oneOf(theaterRepository).findByTheaterId(THEATER_ID);
                will(returnValue(Optional.of(THEATER)));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        TheaterInfo theaterInfo = theaterService.getTheater(THEATER_ID);
        assertThat(theaterInfo.getCustomSiteCode(), is(equalTo(THEATER.getCustomSite().getCustomSiteCode())));
        assertThat(theaterInfo.getCreatorIssuerId(), is(equalTo(THEATER.getCreatorIssuerId())));
        assertThat(theaterInfo.getCreatorSubjectId(), is(equalTo(THEATER.getCreatorSubjectId())));
    }

    @Test
    public void getTheaterReturnsEmptyResults() {
        context.checking(new Expectations() {
            {
                oneOf(theaterRepository).findByTheaterId("test");
                will(returnValue(Optional.empty()));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        Assertions.assertThrows(NotFoundException.class, () -> {
            theaterService.getTheater("test");
        });
    }

    @Test
    public void getTheaterHistory() {
        THEATER_HISTORIES.add(THEATER_HISTORY);
        context.checking(new Expectations() {
            {
                oneOf(theaterRepository).findByTheaterId(THEATER_ID);
                will(returnValue(Optional.of(THEATER)));
                oneOf(theaterHistoryRepository).findByTheaterOrderByInsertedAtDesc(THEATER);
                will(returnValue(THEATER_HISTORIES));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        List<TheaterInfo> theaterInfoList = theaterService.getTheaterHistory(THEATER_ID);
        assertThat(theaterInfoList.get(0).getCustomSiteCode(), is(equalTo(THEATER.getCustomSite().getCustomSiteCode())));
        assertThat(theaterInfoList.get(0).getCreatorIssuerId(), is(equalTo(THEATER.getCreatorIssuerId())));
        assertThat(theaterInfoList.get(0).getCreatorSubjectId(), is(equalTo(THEATER.getCreatorSubjectId())));
    }

    @Test
    public void getTheaterHistoryReturnsEmptyResults() {
        context.checking(new Expectations() {
            {
                oneOf(theaterRepository).findByTheaterId("test2");
                will(returnValue(Optional.empty()));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        Assertions.assertThrows(NotFoundException.class, () -> {
            theaterService.getTheaterHistory("test2");
        });
    }

    @Test
    public void consumeTheaterCreatedEventStatusPending() {
        context.checking(new Expectations() {
            {
                oneOf(theaterRepository).findByTheaterId(THEATER_ID);
                will(returnValue(Optional.of(THEATER_PENDING)));
                oneOf(theaterHistoryRepository).save(THEATER_HISTORY_CREATED);
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        theaterService.consumeTheaterCreatedEvent(THEATER_CREATED_EVENT);
    }

    @Test
    public void consumeTheaterCreatedEventStatusCreated() {
        context.checking(new Expectations() {
            {
                oneOf(theaterRepository).findByTheaterId(THEATER_ID);
                will(returnValue(Optional.of(THEATER_CREATED)));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        theaterService.consumeTheaterCreatedEvent(THEATER_CREATED_EVENT);
    }

    @Test
    public void consumeTheaterCreatedEventTheaterNotFound() {
        context.checking(new Expectations() {
            {
                oneOf(theaterRepository).findByTheaterId(THEATER_ID);
                will(returnValue(Optional.empty()));
                oneOf(customSiteRepository).save(with(any(CustomSite.class)));
                oneOf(theaterRepository).save(with(any(Theater.class)));
                oneOf(theaterHistoryRepository).save(with(any(TheaterHistory.class)));
            }
        });

        TheaterService theaterService = new TheaterServiceImpl(customSiteRepository, theaterRepository, theaterHistoryRepository);
        theaterService.consumeTheaterCreatedEvent(THEATER_CREATED_EVENT);
    }
}
