package com.mazzama.apigatewayservice.model;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Created by azzam on 28/03/19.
 */
@Document @Data
public class User {

  @Id
  private String id;

  @Email(message = "Please provide a valid email")
  @NotEmpty(message = "Please provide an email")
  private String email;

  @Size(min = 6, message = "Please provide password with minimum 6 characters")
  @NotEmpty(message = "Please provide your password")
  private String password;

  @Size(min = 3, message = "Please provide name with minimum 3 characters")
  @NotEmpty(message = "Please provide your name")
  private String name;

  private Integer active = 1;
  private Boolean isLocked = false;
  private Boolean isExpired = false;
  private Boolean isEnabled = false;
  private Set<Role> role;
}
