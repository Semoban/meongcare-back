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
public class DeletedMember {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String providerId;
    private LocalDateTime revokedAt;

    public DeletedMember(String providerId) {
        this.providerId = providerId;
        this.revokedAt = LocalDateTime.now();
    }

    public static DeletedMember of(String providerId) {
        return new DeletedMember(providerId);
    }

}
