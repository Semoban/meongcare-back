package com.meongcare.domain.notifciation.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;

    private Long userId;

    private Long dogId;

    @Builder
    public NotificationRecord(NotificationType notificationType, Long userId, Long dogId) {
        this.notificationType = notificationType;
        this.userId = userId;
        this.dogId = dogId;
    }
}
