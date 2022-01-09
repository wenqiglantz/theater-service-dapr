package com.github.wenqiglantz.service.theaterservice.persistence.entity;

import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class TheaterHistoryTest {

    private static final String THEATER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";
    private static final String CREATOR_SUBJECT_ID = "CREATOR_SUBJECT_ID";
    private static final String CREATOR_ISSUER_ID = "CREATOR_ISSUER_ID";
    private static final CustomSite CUSTOM_SITE = CustomSite.builder().customSiteCode(CUSTOM_SITE_CODE).build();
    private static final Theater THEATER = Theater.builder()
            .theaterId(THEATER_ID)
            .customSite(CUSTOM_SITE)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .build();

    @Test
    public void objectCreation() {
        TheaterHistory object = TheaterHistory.builder()
                .theater(THEATER)
                .status(TheaterStatus.CREATED)
                .build();
        assertThat(object.getTheater(), is(sameInstance(THEATER)));
        assertThat(object.getStatus(), is(sameInstance(TheaterStatus.CREATED)));
    }
}
