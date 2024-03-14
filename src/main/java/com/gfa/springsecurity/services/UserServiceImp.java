package com.gfa.springsecurity.services;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImp implements UserService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserServiceImp(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    private static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetailsService userRegister(UserInfo userInfo) {
        UserDetails newUser = User.builder()
                .username(userInfo.getUsername())
                .password(passwordEncoder().encode(userInfo.getPassword()))
                .roles("user")
                .build();

        userInfoRepository.save(new UserInfo(newUser.getUsername(), newUser.getPassword()));

        return new InMemoryUserDetailsManager(newUser);
    }

    @Override
    public UserDetails findByUsername(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        return User.builder()
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }

    @Override
    public boolean authenticate(String username, String password) {
        UserDetails userDetails = findByUsername(username);
        return userDetails != null && passwordEncoder().matches(password, userDetails.getPassword());
    }
}
