package com.meongcare.domain.dog.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareWaiting {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long dogId;
    private Long requesterId;
    private Long acceptorId;


    private ShareWaiting(Long dogId, Long requesterId, Long acceptorId) {
        this.dogId = dogId;
        this.requesterId = requesterId;
        this.acceptorId = acceptorId;
    }

    public static ShareWaiting of (Long dogId, Long requesterId, Long acceptorId) {
        return new ShareWaiting(dogId, requesterId, acceptorId);
    }
}
