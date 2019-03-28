package com.mazzama.apigatewayservice.configuration.security;

import com.mazzama.apigatewayservice.exception.UserException;
import com.mazzama.apigatewayservice.model.MongoUserDetails;
import com.mazzama.apigatewayservice.model.Role;
import com.mazzama.apigatewayservice.model.User;
import com.mazzama.apigatewayservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by azzam on 28/03/19.
 */
@Service("userDetailsService")
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email);
    if (user == null || user.getRole() == null || user.getRole().isEmpty()){
      throw new UserException("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    String[] authorities = new String[user.getRole().size()];
    int count = 0;
    for (Role role : user.getRole()) {
      authorities[count] = "ROLE"+role.getRole();
      count++;
    }

    MongoUserDetails userDetails = new MongoUserDetails(user.getEmail(),
            user.getPassword(), user.getActive(), user.getIsLocked(),
            user.getIsExpired(), user.getIsEnabled(), authorities);
    return userDetails;
  }
}
