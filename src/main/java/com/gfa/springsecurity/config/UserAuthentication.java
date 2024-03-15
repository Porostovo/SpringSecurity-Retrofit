package com.gfa.springsecurity.config;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserAuthentication implements AuthenticationProvider {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthentication(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Optional<UserInfo> user = userInfoRepository.findByUsername(username);

        if (user.isPresent()){
            if (passwordEncoder.matches(pwd, user.get().getPassword())){
                List<GrantedAuthority> authorities = new ArrayList<>();
          //      authorities.add(new SimpleGrantedAuthority(user.get().getRole()));
                return new UsernamePasswordAuthenticationToken(user, pwd, authorities);
            } else {
                throw new BadCredentialsException("Incorrect username or password");
            }
        } else {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
