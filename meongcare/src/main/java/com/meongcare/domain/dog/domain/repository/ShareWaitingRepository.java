package com.meongcare.domain.dog.domain.repository;

import com.meongcare.domain.dog.domain.entity.ShareWaiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareWaitingRepository extends JpaRepository<ShareWaiting, Long> {
    void deleteByAcceptorIdAndDogId(Long acceptorId, Long DogId);
}
