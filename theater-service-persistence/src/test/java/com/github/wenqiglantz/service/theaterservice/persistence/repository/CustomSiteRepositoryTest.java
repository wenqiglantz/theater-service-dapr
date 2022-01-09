package com.github.wenqiglantz.service.theaterservice.persistence.repository;

import com.github.wenqiglantz.service.theaterservice.persistence.entity.CustomSite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_CLASS)
@DataJpaTest
public class CustomSiteRepositoryTest {
    private static final String CUSTOM_SITE_ID = "ABCDEFG12345678910HIJKLMNOP12346";
    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";

    private static final CustomSite CUSTOM_SITE = CustomSite.builder()
            .customSiteId(CUSTOM_SITE_ID)
            .customSiteCode(CUSTOM_SITE_CODE)
            .build();

    @Autowired
    private CustomSiteRepository customSiteRepository;

    @BeforeEach
    public void cleanup() {
        customSiteRepository.deleteAll();
    }

    @Test
    public void findByCustomSiteId() {
        customSiteRepository.save(CUSTOM_SITE);
        Optional<CustomSite> newCustomSite = customSiteRepository.findByCustomSiteId(CUSTOM_SITE_ID);
        assertThat(newCustomSite.isPresent(), is(equalTo(true)));
        assertThat(newCustomSite.get(), is(equalTo(CUSTOM_SITE)));
    }

    @Test
    public void findByCustomSiteIdReturnsNoResults() {
        Optional<CustomSite> newCustomSite = customSiteRepository.findByCustomSiteId("differentId");
        assertThat(newCustomSite.isPresent(), is(equalTo(false)));
    }
}
