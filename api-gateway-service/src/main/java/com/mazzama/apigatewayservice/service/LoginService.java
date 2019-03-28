package com.mazzama.apigatewayservice.service;

import com.mazzama.apigatewayservice.model.User;

/**
 * Created by azzam on 28/03/19.
 */
public interface LoginService {

  String login(String username, String password);
  User saveUser(User user);
  boolean logout(String token);
  Boolean isValidToken(String token);
  String createNewToken(String token);
}
