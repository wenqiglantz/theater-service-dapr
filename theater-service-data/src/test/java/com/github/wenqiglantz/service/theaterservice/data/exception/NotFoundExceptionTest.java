package com.github.wenqiglantz.service.theaterservice.data.exception;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class NotFoundExceptionTest {
    private static final String MESSAGE = "MESSAGE";

    @Test
    public void notFoundException() {
        NotFoundException exception = new NotFoundException(MESSAGE);
        assertThat(exception.getMessage(), is(equalTo(MESSAGE)));
    }
}
