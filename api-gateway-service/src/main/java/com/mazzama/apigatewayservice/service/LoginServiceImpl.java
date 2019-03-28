package com.mazzama.apigatewayservice.service;

import com.mazzama.apigatewayservice.configuration.security.JwtTokenProvider;
import com.mazzama.apigatewayservice.exception.UserException;
import com.mazzama.apigatewayservice.model.JwtToken;
import com.mazzama.apigatewayservice.model.Role;
import com.mazzama.apigatewayservice.model.User;
import com.mazzama.apigatewayservice.repository.JwtTokenRepository;
import com.mazzama.apigatewayservice.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by azzam on 28/03/19.
 */
@Service
public class LoginServiceImpl implements LoginService {

  private PasswordEncoder passwordEncoder;
  private JwtTokenProvider jwtTokenProvider;
  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private JwtTokenRepository jwtTokenRepository;

  private LoginServiceImpl(final PasswordEncoder passwordEncoder, final JwtTokenProvider jwtTokenProvider,
          final AuthenticationManager authenticationManager, final UserRepository userRepository,
          final JwtTokenRepository jwtTokenRepository) {
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.jwtTokenRepository = jwtTokenRepository;
  }

  @Override
  public String login(final String username, final String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      User user = userRepository.findByEmail(username);
      if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
        throw new UserException("Invalid username or password", HttpStatus.UNAUTHORIZED);
      }

      return jwtTokenProvider.createToken(username, user.getRole().stream()
              .map((Role role) -> "ROLE_"+role.getRole()).filter(Objects::nonNull).collect(Collectors.toList()));
    } catch (AuthenticationException e) {
      throw new UserException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
    }
}

  @Override
  public User saveUser(final User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public boolean logout(final String token) {
    jwtTokenRepository.delete(new JwtToken(token));
    return true;
  }

  @Override
  public Boolean isValidToken(final String token) {
    return jwtTokenProvider.validateToken(token);
  }

  @Override
  public String createNewToken(final String token) {
    String username = jwtTokenProvider.getUsername(token);
    List<String> roleList = jwtTokenProvider.getRoleList(token);
    return jwtTokenProvider.createToken(username, roleList);
  }
}
