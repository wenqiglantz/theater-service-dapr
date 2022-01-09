package com.github.wenqiglantz.service.theaterservice.persistence.repository;

import com.github.wenqiglantz.service.theaterservice.persistence.entity.CustomSite;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.Theater;
import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
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
public class TheaterRepositoryTest {
    private static final String CUSTOM_SITE_ID = "ABCDEFG12345678910HIJKLMNOP12346";
    private static final String CUSTOM_SITE_CODE = "CUSTOM_SITE_CODE";
    private static final String THEATER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String CREATOR_SUBJECT_ID = "CREATOR_SUBJECT_ID";
    private static final String CREATOR_ISSUER_ID = "CREATOR_ISSUER_ID";

    private static final CustomSite CUSTOM_SITE = CustomSite.builder()
            .customSiteId(CUSTOM_SITE_ID)
            .customSiteCode(CUSTOM_SITE_CODE)
            .build();

    private static final Theater THEATER = Theater.builder()
            .theaterId(THEATER_ID)
            .customSite(CUSTOM_SITE)
            .creatorSubjectId(CREATOR_SUBJECT_ID)
            .creatorIssuerId(CREATOR_ISSUER_ID)
            .status(TheaterStatus.PENDING)
            .build();

    @Autowired
    private CustomSiteRepository customSiteRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @BeforeEach
    public void cleanup() {
        customSiteRepository.deleteAll();
        theaterRepository.deleteAll();
    }

    @Test
    public void findByTheaterId() {
        customSiteRepository.save(CUSTOM_SITE);
        theaterRepository.save(THEATER);
        Optional<Theater> newTheater = theaterRepository.findByTheaterId(THEATER_ID);
        assertThat(newTheater.isPresent(), is(equalTo(true)));
        assertThat(newTheater.get(), is(equalTo(THEATER)));
    }

    @Test
    public void findByTheaterIdReturnsNoResults() {
        Optional<Theater> newTheater = theaterRepository.findByTheaterId("differentId");
        assertThat(newTheater.isPresent(), is(equalTo(false)));
    }
}
