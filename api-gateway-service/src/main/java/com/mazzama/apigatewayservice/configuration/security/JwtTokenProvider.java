package com.mazzama.apigatewayservice.configuration.security;

import com.mazzama.apigatewayservice.model.JwtToken;
import com.mazzama.apigatewayservice.model.MongoUserDetails;
import com.mazzama.apigatewayservice.repository.JwtTokenRepository;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by azzam on 28/03/19.
 */
@Component
public class JwtTokenProvider {

  private static final String AUTH = "auth";
  private static final String AUTHORIZATION = "AUTHORIZATION";
  private String secretKey = "secret-key";
  private long validityInMilliseconds = 7200000;

  @Autowired
  private JwtTokenRepository jwtTokenRepository;

  @Autowired
  private UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, List<String> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put(AUTH, roles);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256,secretKey)
            .compact();
    jwtTokenRepository.save(new JwtToken(token));
    return token;
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader(AUTHORIZATION);
    if (bearerToken != null) {
      return bearerToken;
    }
    return null;
  }

  public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
    Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    return true;
  }

  public boolean isTokenPresentInDB(String token) {
    return jwtTokenRepository.findById(token).isPresent();
  }

  public UserDetails getUserDetails(String token) {
    String username = getUsername(token);
    List<String> roleLists = getRoleList(token);
    UserDetails userDetails = new MongoUserDetails(username, roleLists.toArray(new String[roleLists.size()]));
    return userDetails;
  }

  public List<String> getRoleList(final String token) {
    return (List<String>) Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody().get(AUTH);
  }

  public String getUsername(final String token) {
    return Jwts.parser().setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public Authentication getAuthentication(String token) {
    //UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    UserDetails userDetails = getUserDetails(token);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

}
