package com.mazzama.apigatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by azzam on 28/03/19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
  public String accessToken;
}
