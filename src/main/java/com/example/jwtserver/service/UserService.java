package com.example.jwtserver.service;

import com.example.jwtserver.dto.SignupRequestDto;
import com.example.jwtserver.model.User;
import com.example.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /* 회원가입 */
    public User createUser(SignupRequestDto signuprRequestDto){
        signuprRequestDto.setPassword(bCryptPasswordEncoder.encode(signuprRequestDto.getPassword()));
        User user = new User(signuprRequestDto);
        return userRepository.save(user);
    }

    /* 아이디 중복 체크 */
    public String usernameCheck(String username){
        Optional<User> user= userRepository.findByUsername(username);
        if(user.isEmpty()){ // user이 비어있다 => 중복 아이디가 없다.
            return "true";
        }else{
            return "false";
        }
    }

    /* 이메일 중복 체크 */
    public String emailCheck(String email){
        Optional<User> user= userRepository.findByEmail(email);
        if(user.isEmpty()){ //user이 비어있다 => 중복 이메일이 없다
            return "true";
        }else{
            return "false";
        }
    }
}
