package com.meongcare.domain.dog.domain.repository;

import com.meongcare.domain.dog.domain.entity.Dog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meongcare.domain.dog.domain.entity.QDog.dog;

@Repository
@RequiredArgsConstructor
public class DogQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Dog> findAllByIds(List<Long> dogIds) {
        return jpaQueryFactory
                .selectFrom(dog)
                .where(dog.id.in(dogIds))
                .fetch();
    }
}
