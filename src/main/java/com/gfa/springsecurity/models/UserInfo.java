package com.gfa.springsecurity.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;

}
