package com.github.wenqiglantz.service.theaterservice.persistence.repository;

import com.github.wenqiglantz.service.theaterservice.persistence.entity.TheaterHistory;
import com.github.wenqiglantz.service.theaterservice.persistence.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterHistoryRepository extends JpaRepository<TheaterHistory, String> {
    List<TheaterHistory> findByTheater(Theater theater);

    List<TheaterHistory> findByTheaterOrderByInsertedAtDesc(Theater theater);
}
