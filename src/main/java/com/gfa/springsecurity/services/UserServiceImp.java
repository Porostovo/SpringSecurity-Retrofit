package com.gfa.springsecurity.services;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImp implements UserService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserServiceImp(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    private static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetailsService userRegister(UserInfo userInfo) {

        try {
            if (userInfoRepository.findByUsername(userInfo.getUsername()).isEmpty()) {
                //this works, but its better to use UserDetails newUser = User.builder()
                //userInfoRepository.save(new UserInfo(userInfo.getUsername(), passwordEncoder().encode(userInfo.getPassword())));

                UserDetails newUser = User.builder()
                        .username(userInfo.getUsername())
                        .password(passwordEncoder().encode(userInfo.getPassword()))
                        .roles("user")
                        .build();
                userInfoRepository.save(new UserInfo(newUser.getUsername(), newUser.getPassword()));

                //return null; //new InMemoryUserDetailsManager(newUser); we do not use
            } else {
                throw new BadCredentialsException("User with this username already exists");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("User with this username already exists");
        }
        return null;
    }
}
