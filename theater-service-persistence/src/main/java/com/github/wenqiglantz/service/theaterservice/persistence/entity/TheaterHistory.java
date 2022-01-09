package com.github.wenqiglantz.service.theaterservice.persistence.entity;

import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "THEATER_HISTORY")
public class TheaterHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "THEATER_ID")
    private Theater theater;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private TheaterStatus status;
}
