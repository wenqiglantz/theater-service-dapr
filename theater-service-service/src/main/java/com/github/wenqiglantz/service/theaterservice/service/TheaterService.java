package com.github.wenqiglantz.service.theaterservice.service;

import com.github.wenqiglantz.service.theaterservice.data.TheaterInfo;
import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import com.github.wenqiglantz.service.theaterservice.data.event.TheaterCreatedEvent;

import java.util.List;

public interface TheaterService {

    TheaterInfo saveTheater(TheaterInfo theaterInfo, TheaterStatus theaterStatus);

    TheaterInfo getTheater(String theaterId);

    List<TheaterInfo> getTheaterHistory(String theaterId);

    void consumeTheaterCreatedEvent(TheaterCreatedEvent theaterCreatedEvent);


}