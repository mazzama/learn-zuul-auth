package com.mazzama.apigatewayservice.configuration.security;

import com.mazzama.apigatewayservice.exception.UserException;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.JwtException;

/**
 * Created by azzam on 28/03/19.
 */
public class JwtTokenFilter extends GenericFilterBean {

  private JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
          throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
    if (token != null) {
      if (!jwtTokenProvider.isTokenPresentInDB(token)) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT token");
        throw new UserException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
      }
      try {
        jwtTokenProvider.validateToken(token) ;
      } catch (JwtException | IllegalArgumentException e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT token");
        throw new UserException("Invalid JWT token",HttpStatus.UNAUTHORIZED);
      }
      Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    chain.doFilter(req, res);

  }
}
