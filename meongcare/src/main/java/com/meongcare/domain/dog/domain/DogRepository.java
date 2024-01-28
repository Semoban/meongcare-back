package com.meongcare.domain.dog.domain;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.EntityNotFoundException;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    default Dog getDog(Long id) {
        return this.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DOG_ENTITY_NOT_FOUND));
    }

    Optional<Dog> findByIdAndDeletedFalse(Long id);

    List<Dog> findAllByMemberAndDeletedFalse(Member member);

    @Modifying
    @Query("UPDATE Dog d SET d.deleted = true WHERE d.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}
