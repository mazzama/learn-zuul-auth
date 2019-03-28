package com.mazzama.apigatewayservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by azzam on 28/03/19.
 */
public class UserException extends RuntimeException{

  private static final long serialVersionUID = 2L;

  private final String message;
  private final HttpStatus httpStatus;

  public UserException(final String message, final HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
