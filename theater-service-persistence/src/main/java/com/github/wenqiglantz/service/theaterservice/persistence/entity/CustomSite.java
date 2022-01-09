package com.github.wenqiglantz.service.theaterservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUSTOM_SITE")
public class CustomSite extends BaseEntity {

    @Column(name = "CUSTOM_SITE_ID")
    private String customSiteId;

    @Column(name = "CUSTOM_SITE_CODE")
    private String customSiteCode;

}
