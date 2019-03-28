package com.mazzama.apigatewayservice.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by azzam on 28/03/19.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Disable Cross Site Request Forgery (CSRF)
    http.cors().and().csrf().disable();

    // No created will be created or used by Spring Security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
            .antMatchers("/**/signin/**").permitAll()
            .anyRequest().authenticated();

    http.exceptionHandling().accessDeniedPage("/login");

    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow eureka client to be accessed without authentication
    web.ignoring().antMatchers("/*/")//
            .antMatchers("/eureka/**")//
            .antMatchers(HttpMethod.OPTIONS, "/**"); // Request type options should be allowed.
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public AuthenticationManager customAuthenticationManager() throws Exception {
    return authenticationManager();
  }
}
