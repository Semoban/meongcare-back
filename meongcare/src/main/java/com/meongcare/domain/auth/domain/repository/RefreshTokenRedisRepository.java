package com.meongcare.domain.auth.domain.repository;

import com.meongcare.domain.auth.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
