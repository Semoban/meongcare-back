package com.meongcare.domain.excreta.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Excreta extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ExcretaType type;

    private LocalDateTime dateTime;

    private String imageURL;

    private Long dogId;

    @Builder
    public Excreta(ExcretaType type, LocalDateTime dateTime, String imageURL, Long dogId) {
        this.type = type;
        this.dateTime = dateTime;
        this.imageURL = imageURL;
        this.dogId = dogId;
    }

    public static Excreta of(@NotNull String excreta, LocalDateTime dateTime, String imageURL, Long id) {
        return Excreta.builder()
                .type(ExcretaType.of(excreta))
                .dateTime(dateTime)
                .imageURL(imageURL)
                .dogId(id)
                .build();
    }

    public void updateRecord(ExcretaType type, LocalDateTime dateTime, String imageURL) {
        this.type = type;
        this.dateTime = dateTime;
        if (imageURL.isEmpty()) {
            return;
        }
        this.imageURL = imageURL;
    }
}
