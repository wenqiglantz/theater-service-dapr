package com.github.wenqiglantz.service.theaterservice.persistence.entity;

import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "THEATER")
public class Theater extends BaseEntity {

    @Column(name = "THEATER_ID")
    private String theaterId;

    @OneToOne
    @JoinColumn(name = "CUSTOM_SITE_ID")
    private CustomSite customSite;

    @Column(name = "CREATOR_SUBJECT_ID")
    private String creatorSubjectId;

    @Column(name = "CREATOR_ISSUER_ID")
    private String creatorIssuerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private TheaterStatus status;
}
