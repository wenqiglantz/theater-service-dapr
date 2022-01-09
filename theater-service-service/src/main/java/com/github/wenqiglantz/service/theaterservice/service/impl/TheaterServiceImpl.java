package com.github.wenqiglantz.service.theaterservice.service.impl;

import com.github.wenqiglantz.service.theaterservice.data.TheaterInfo;
import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import com.github.wenqiglantz.service.theaterservice.data.event.TheaterCreatedEvent;
import com.github.wenqiglantz.service.theaterservice.data.exception.NotFoundException;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.CustomSite;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.Theater;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.TheaterHistory;
import com.github.wenqiglantz.service.theaterservice.persistence.repository.CustomSiteRepository;
import com.github.wenqiglantz.service.theaterservice.persistence.repository.TheaterHistoryRepository;
import com.github.wenqiglantz.service.theaterservice.persistence.repository.TheaterRepository;
import com.github.wenqiglantz.service.theaterservice.service.TheaterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TheaterServiceImpl implements TheaterService {

    private final CustomSiteRepository customSiteRepository;
    private final TheaterRepository theaterRepository;
    private final TheaterHistoryRepository theaterHistoryRepository;
    private final static String DEFAULT = "DEFAULT";

    @Autowired
    public TheaterServiceImpl(CustomSiteRepository customSiteRepository,
                              TheaterRepository theaterRepository,
                              TheaterHistoryRepository theaterHistoryRepository) {
        this.customSiteRepository = customSiteRepository;
        this.theaterRepository = theaterRepository;
        this.theaterHistoryRepository = theaterHistoryRepository;
    }

    @Override
    public TheaterInfo saveTheater(TheaterInfo theaterInfo, TheaterStatus theaterStatus) {
        CustomSite customSite = CustomSite.builder()
                .customSiteId(Strings.isBlank(theaterInfo.getCustomSiteId()) ? UUID.randomUUID().toString() : theaterInfo.getCustomSiteId())
                .customSiteCode(theaterInfo.getCustomSiteCode())
                .build();
        customSiteRepository.save(customSite);
        theaterInfo.setTheaterId(Strings.isBlank(theaterInfo.getTheaterId()) ? UUID.randomUUID().toString() : theaterInfo.getTheaterId());
        Theater theater = Theater.builder()
                .theaterId(theaterInfo.getTheaterId())
                .customSite(customSite)
                .creatorSubjectId(theaterInfo.getCreatorSubjectId())
                .creatorIssuerId(theaterInfo.getCreatorIssuerId())
                .status(theaterStatus)
                .build();
        theaterRepository.save(theater);
        TheaterHistory theaterHistory = TheaterHistory.builder()
                .theater(theater)
                .status(theater.getStatus())
                .build();
        theaterHistoryRepository.save(theaterHistory);
        return theaterInfo;
    }

    @Override
    public TheaterInfo getTheater(String theaterId) {
        Theater theater =
                theaterRepository.findByTheaterId(theaterId).orElseThrow(() ->
                        new NotFoundException("Could not find Theater with theaterId: " + theaterId));

        TheaterInfo theaterInfo = TheaterInfo.builder()
                .theaterId(theaterId)
                .customSiteId(theater.getCustomSite().getCustomSiteId())
                .customSiteCode(theater.getCustomSite().getCustomSiteCode())
                .creatorSubjectId(theater.getCreatorSubjectId())
                .creatorIssuerId(theater.getCreatorIssuerId())
                .status(theater.getStatus().value())
                .build();
        return theaterInfo;
    }

    @Override
    public List<TheaterInfo> getTheaterHistory(String theaterId) {
        Theater theater =
                theaterRepository.findByTheaterId(theaterId).orElseThrow(() ->
                        new NotFoundException("Could not find Theater with theaterId: " + theaterId));

        List<TheaterHistory> theaterHistories = theaterHistoryRepository.findByTheaterOrderByInsertedAtDesc(theater);
        List<TheaterInfo> theaterInfos = theaterHistories.stream()
                .map(p -> TheaterInfo.builder()
                    .status(p.getStatus().value())
                    .theaterId(p.getTheater().getTheaterId())
                    .customSiteCode(p.getTheater().getCustomSite().getCustomSiteCode())
                    .creatorSubjectId(p.getTheater().getCreatorSubjectId())
                    .creatorIssuerId(p.getTheater().getCreatorIssuerId())
                    .build())
                .collect(Collectors.toList());

        return theaterInfos;
    }

    @Override
    public void consumeTheaterCreatedEvent(
            TheaterCreatedEvent theaterCreatedEvent) {
        String theaterId = theaterCreatedEvent.getTheaterId();
        Optional<Theater> theaterOptional = theaterRepository.findByTheaterId(theaterId);
        if (theaterOptional.isPresent()) {
            log.debug(">>> theater exists, updating status");
            Theater theater = theaterOptional.get();
            if (theater.getStatus() != TheaterStatus.CREATED) {
                theater.setStatus(TheaterStatus.CREATED);
                TheaterHistory theaterHistory = TheaterHistory.builder()
                        .theater(theater)
                        .status(theater.getStatus())
                        .build();
                theaterHistoryRepository.save(theaterHistory);
            }
        } else {
            log.debug(">>> theater does not exist, saving theater");
            TheaterInfo theaterInfo = TheaterInfo.builder()
                    .theaterId(theaterId)
                    .customSiteCode(DEFAULT)
                    .creatorSubjectId(DEFAULT)
                    .creatorIssuerId(DEFAULT)
                    .build();
            saveTheater(theaterInfo, TheaterStatus.CREATED);
        }
    }
}
