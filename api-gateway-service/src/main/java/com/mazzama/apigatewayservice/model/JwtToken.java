package com.mazzama.apigatewayservice.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by azzam on 28/03/19.
 */
@Document
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
  private String token;
}
