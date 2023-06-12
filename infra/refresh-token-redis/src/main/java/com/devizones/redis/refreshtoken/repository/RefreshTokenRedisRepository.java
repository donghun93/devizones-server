package com.devizones.redis.refreshtoken.repository;

import com.devizones.redis.refreshtoken.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {

}
