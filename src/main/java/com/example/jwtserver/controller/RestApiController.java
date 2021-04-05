package com.example.jwtserver.controller;

import com.example.jwtserver.auth.PrincipalDetails;
import com.example.jwtserver.model.User;
import com.example.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    @GetMapping("hello")
    public String hello() {
        return "<h1>hello</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody User user) { //user는 나중에 dto로 바꿔야됨
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입완료";
    }

    //user만 접근 가능
    @GetMapping("/api/v1/user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principal.getUsername());
        return "user";
    }

    @GetMapping("/api/v1/cart")
    public String cart() {
        return "<h1>cart</h1>";
    }

}
