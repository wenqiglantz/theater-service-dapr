package com.github.wenqiglantz.service.theaterservice.persistence.repository;

import com.github.wenqiglantz.service.theaterservice.persistence.entity.CustomSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface CustomSiteRepository extends JpaRepository<CustomSite, String> {
    Optional<CustomSite> findByCustomSiteId(String customSiteId);
}
