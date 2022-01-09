package com.github.wenqiglantz.service.theaterservice.persistence.repository;

import com.github.wenqiglantz.service.theaterservice.persistence.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface TheaterRepository extends JpaRepository<Theater, String> {
    Optional<Theater> findByTheaterId(String theaterId);
}
