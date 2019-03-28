package com.mazzama.apigatewayservice.controller;

import com.mazzama.apigatewayservice.model.AuthResponse;
import com.mazzama.apigatewayservice.model.LoginRequest;
import com.mazzama.apigatewayservice.model.User;
import com.mazzama.apigatewayservice.service.LoginService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by azzam on 28/03/19.
 */
@Controller
@RequestMapping("/api")
public class LoginController {

  private LoginService loginService;

  public LoginController(final LoginService loginService) {
    this.loginService = loginService;
  }

  @CrossOrigin("*")
  @PostMapping("/signin")
  @ResponseBody
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
    String token = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
    HttpHeaders headers = new HttpHeaders();
    List<String> headerList = new ArrayList<>();
    List<String> exposeList = new ArrayList<>();
    headerList.add("Content-Type");
    headerList.add("Accept");
    headerList.add("X-Requested-With");
    headerList.add("Authorization");
    headers.setAccessControlAllowHeaders(headerList);
    exposeList.add("Authorization");
    headers.setAccessControlExposeHeaders(exposeList);
    headers.set("Authorization", token);
    return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
  }

  @CrossOrigin("*")
  @PostMapping("/signup")
  @ResponseBody
  public String signup(@Valid @RequestBody LoginRequest loginRequest) {
    User user = new User();
    user.setEmail("default@gmail.com");
    user.setName(loginRequest.getUsername());
    user.setPassword(loginRequest.getPassword());
    user = loginService.saveUser(user);
    return user.getName();
  }

  @CrossOrigin("*")
  @PostMapping("/signout")
  @ResponseBody
  public ResponseEntity<AuthResponse> logout(@RequestHeader(value = "Authorization") String token) {
    HttpHeaders headers = new HttpHeaders();
    if (loginService.logout(token)) {
      headers.remove("Authorization");
      return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
    }
    return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.NOT_MODIFIED);
  }

  @PostMapping("/valid/token")
  @ResponseBody
  public Boolean isValidToken(@RequestHeader(value = "Authorization") String token) {
    return true;
  }

  @CrossOrigin("*")
  @PostMapping("/signin/token")
  @ResponseBody
  public ResponseEntity<AuthResponse> createNewToken(@RequestHeader(value = "Authorization") String token) {
    String newToken = loginService.createNewToken(token);
    HttpHeaders headers = new HttpHeaders();
    List<String> headerList = new ArrayList<>();
    List<String> exposeList = new ArrayList<>();
    headerList.add("Content-Type");
    headerList.add("Accept");
    headerList.add("X-Requested-With");
    headerList.add("Authorization");
    headers.setAccessControlAllowHeaders(headerList);
    exposeList.add("Authorization");
    headers.setAccessControlExposeHeaders(exposeList);
    headers.set("Authorization", newToken);
    return new ResponseEntity<AuthResponse>(new AuthResponse(newToken), headers, HttpStatus.CREATED);
  }

}
