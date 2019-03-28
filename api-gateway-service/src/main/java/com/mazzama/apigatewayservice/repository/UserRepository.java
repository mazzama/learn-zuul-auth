package com.mazzama.apigatewayservice.repository;

import com.mazzama.apigatewayservice.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by azzam on 28/03/19.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

  @Query(value= "{'email' : ?0}")
  User findByEmail(String email);
}
