package com.mazzama.apigatewayservice.repository;

import com.mazzama.apigatewayservice.model.JwtToken;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by azzam on 28/03/19.
 */
@Repository
public interface JwtTokenRepository extends MongoRepository<JwtToken, String> {
}
