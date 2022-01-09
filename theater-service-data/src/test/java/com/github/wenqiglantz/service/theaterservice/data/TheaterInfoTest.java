package com.github.wenqiglantz.service.theaterservice.data;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class TheaterInfoTest {

    private static final String THEATER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";
    private static final String CREATOR_SUBJECT_ID = "CREATOR_SUBJECT_ID";
    private static final String CREATOR_ISSUER_ID = "CREATOR_ISSUER_ID";

    @Test
    public void objectCreation() {
        TheaterInfo object = TheaterInfo.builder()
                .theaterId(THEATER_ID)
                .customSiteCode(CUSTOM_SITE_CODE)
                .creatorSubjectId(CREATOR_SUBJECT_ID)
                .creatorIssuerId(CREATOR_ISSUER_ID)
                .build();
        assertThat(object.getCustomSiteCode(), is(sameInstance(CUSTOM_SITE_CODE)));
        assertThat(object.getTheaterId(), is(sameInstance(THEATER_ID)));
        assertThat(object.getCreatorSubjectId(), is(sameInstance(CREATOR_SUBJECT_ID)));
        assertThat(object.getCreatorIssuerId(), is(sameInstance(CREATOR_ISSUER_ID)));
    }
}
