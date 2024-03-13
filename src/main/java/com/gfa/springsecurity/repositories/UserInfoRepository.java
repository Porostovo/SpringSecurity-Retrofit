package com.gfa.springsecurity.repositories;

import com.gfa.springsecurity.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
