package com.github.wenqiglantz.service.theaterservice.data.common;

public enum TheaterStatus {
    PENDING,
    CREATED;

    public String value() {
        return name();
    }

    public static TheaterStatus fromValue(String v) {
        return valueOf(v);
    }
}
