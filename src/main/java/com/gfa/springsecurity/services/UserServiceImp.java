package com.gfa.springsecurity.services;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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
        UserDetails newUser = User.builder()
                .username(userInfo.getUsername())
                .password(passwordEncoder().encode(userInfo.getPassword()))
                .roles("user")
                .build();

        if (userInfoRepository.findByUsername(userInfo.getUsername()).isEmpty()){
            userInfoRepository.save(new UserInfo(newUser.getUsername(), newUser.getPassword()));
        } else throw new BadCredentialsException("user with this username already exists");





        return new InMemoryUserDetailsManager(newUser);
    }

//    @Override
//    public UserDetails findByUsername(String username) {
//        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUsername(username);
////        UserInfo userInfo = userInfoRepository.findByUsername(username);
// //       UserInfo userInfo = userInfoOptional.get().
//        return User.builder()
//                .username(userInfoOptional.get().getUsername())
//                .password(userInfoOptional.get().getPassword())
//                .build();
// }

//    @Override
//    public boolean authenticate(String username, String password) {
//        UserDetails userDetails = findByUsername(username);
//        return userDetails != null && passwordEncoder().matches(password, userDetails.getPassword());
//    }
}

