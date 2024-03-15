package com.gfa.springsecurity.config;

import com.gfa.springsecurity.models.UserInfo;
import com.gfa.springsecurity.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthentication userAuthentication;
    private final UserServiceImp userServiceImp;
    @Autowired
    public SecurityConfig(UserAuthentication userAuthentication, UserServiceImp userServiceImp) {
        this.userAuthentication = userAuthentication;
        this.userServiceImp = userServiceImp;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/login/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/movieList", true)
                                .failureUrl("/index")
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.builder()
                .username("aaa")
                .password(passwordEncoder().encode("bbb"))
                .roles("user")
                .build();

        userServiceImp.userRegister(new UserInfo(user.getUsername(), "bbb"));

        UserDetails admin = User.builder()
                .username("ccc")
                .password(passwordEncoder().encode("ddd"))
                .roles("admin")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(userAuthentication);
        return authenticationManagerBuilder.build();
    }
}
