package com.meongcare.domain.dog.domain;

import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    default Dog getById(Long id) {
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    List<Dog> findAllByMember(Member member);
}
