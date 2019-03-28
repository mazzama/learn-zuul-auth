package com.mazzama.apigatewayservice.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by azzam on 28/03/19.
 */
@RestControllerAdvice
public class CustomExceptionHandler {

  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(UserException.class)
  public void handleUserException(HttpServletResponse res, UserException e) throws IOException {
    LOG.error("ERROR", e);
    res.sendError(e.getHttpStatus().value(), e.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public void handleAccessDeniedException(HttpServletResponse res, UserException e) throws IOException {
    LOG.error("ERROR", e);
    res.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public void illegalArgumentException(HttpServletResponse res, IllegalArgumentException e) throws IOException {
    LOG.error("ERROR", e);
    res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
  }

  @ExceptionHandler(Exception.class)
  public void handleException(HttpServletResponse res, Exception e) throws  IOException {
    LOG.error("ERROR", e);
    res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
  }
}
