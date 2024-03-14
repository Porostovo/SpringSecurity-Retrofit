package com.gfa.springsecurity.repositories;

import com.gfa.springsecurity.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

   // boolean userExists(String username);
    Optional<UserInfo> findByUsername(String username);
}
