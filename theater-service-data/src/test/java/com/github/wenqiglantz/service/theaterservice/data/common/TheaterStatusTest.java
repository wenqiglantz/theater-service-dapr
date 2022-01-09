package com.github.wenqiglantz.service.theaterservice.data.common;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TheaterStatusTest {
    private static final String PENDING = "PENDING";
    private static final String CREATED = "CREATED";

    @Test
    public void getName() {
        assertThat(TheaterStatus.PENDING.value(), is(equalTo(PENDING)));
        assertThat(TheaterStatus.CREATED.value(), is(equalTo(CREATED)));
    }

    @Test
    public void fromValue(){
        assertThat(TheaterStatus.fromValue(PENDING), is(equalTo(TheaterStatus.PENDING)));
        assertThat(TheaterStatus.fromValue(CREATED), is(equalTo(TheaterStatus.CREATED)));
    }
}
