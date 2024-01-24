package com.meongcare.domain.excreta.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    @Builder
    public Excreta(ExcretaType type, LocalDateTime dateTime, String imageURL, Dog dog) {
        this.type = type;
        this.dateTime = dateTime;
        this.imageURL = imageURL;
        this.dog = dog;
    }

    public static Excreta of(@NotNull String excreta, LocalDateTime dateTime, String imageURL, Dog dog) {
        return Excreta.builder()
                .type(ExcretaType.of(excreta))
                .dateTime(dateTime)
                .imageURL(imageURL)
                .dog(dog)
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
