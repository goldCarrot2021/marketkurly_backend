package com.example.jwtserver.repository;

import com.example.jwtserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    public User findByUsername(String username);

    /* 아이디 중복 찾기 */
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
