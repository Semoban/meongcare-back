package com.meongcare.domain.dog.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogWeight {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    @NotNull
    private double weight;

    @CreationTimestamp
    private LocalDateTime date;

    @Builder
    public DogWeight(Dog dog, double weight) {
        this.dog = dog;
        this.weight = weight;
    }

    public static DogWeight of(Dog dog, double weight) {
        return DogWeight
                .builder()
                .dog(dog)
                .weight(weight)
                .build();
    }
}
