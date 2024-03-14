package com.gfa.springsecurity.services;

import com.gfa.springsecurity.models.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userRegister(UserInfo userInfo);
 //   UserDetails findByUsername(String username);

   // boolean authenticate(String username, String password);
}
