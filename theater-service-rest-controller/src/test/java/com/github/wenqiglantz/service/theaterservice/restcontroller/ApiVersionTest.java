package com.github.wenqiglantz.service.theaterservice.restcontroller;

import org.junit.jupiter.api.Test;

import static com.github.wenqiglantz.service.theaterservice.restcontroller.ApiVersion.JSON;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ApiVersionTest {

    @Test
    public void equals1() {
        assertThat(JSON, is(equalTo("application/json")));
    }
}
