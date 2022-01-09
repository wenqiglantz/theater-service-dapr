package com.github.wenqiglantz.service.theaterservice.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class CustomSiteTest {

    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";

    @Test
    public void objectCreation() {
        CustomSite object = CustomSite.builder().customSiteCode(CUSTOM_SITE_CODE).build();
        assertThat(object.getCustomSiteCode(), is(sameInstance(CUSTOM_SITE_CODE)));
    }
}
