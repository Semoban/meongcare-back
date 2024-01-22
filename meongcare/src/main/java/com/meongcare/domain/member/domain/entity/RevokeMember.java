package com.meongcare.domain.member.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RevokeMember {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String providerId;
    private LocalDateTime revokeDate;

    public RevokeMember(String providerId) {
        this.providerId = providerId;
        this.revokeDate = LocalDateTime.now();
    }

    public static RevokeMember of(String providerId) {
        return new RevokeMember(providerId);
    }

}
