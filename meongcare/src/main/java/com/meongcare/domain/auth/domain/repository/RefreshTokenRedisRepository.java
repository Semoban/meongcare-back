package com.meongcare.domain.auth.domain.repository;

import com.meongcare.domain.auth.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken,Long> {

}
