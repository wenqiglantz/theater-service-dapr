package com.github.wenqiglantz.service.theaterservice.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class TheaterTest {

    private static final String THEATER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";
    private static final String CREATOR_SUBJECT_ID = "CREATOR_SUBJECT_ID";
    private static final String CREATOR_ISSUER_ID = "CREATOR_ISSUER_ID";
    private static final CustomSite CUSTOM_SITE = CustomSite.builder().customSiteCode(CUSTOM_SITE_CODE).build();

    @Test
    public void objectCreation() {
        Theater object = Theater.builder()
                .theaterId(THEATER_ID)
                .customSite(CUSTOM_SITE)
                .creatorSubjectId(CREATOR_SUBJECT_ID)
                .creatorIssuerId(CREATOR_ISSUER_ID)
                .build();
        assertThat(object.getCustomSite(), is(sameInstance(CUSTOM_SITE)));
        assertThat(object.getTheaterId(), is(sameInstance(THEATER_ID)));
        assertThat(object.getCreatorSubjectId(), is(sameInstance(CREATOR_SUBJECT_ID)));
        assertThat(object.getCreatorIssuerId(), is(sameInstance(CREATOR_ISSUER_ID)));
    }
}
