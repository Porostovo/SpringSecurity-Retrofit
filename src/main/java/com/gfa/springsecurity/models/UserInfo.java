package com.gfa.springsecurity.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class UserInfo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;

    public UserInfo(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;

    }
    public UserInfo(String username, String password){
        this.username = username;
        this.password = password;

    }

}
