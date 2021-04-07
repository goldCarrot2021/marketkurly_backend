package com.example.jwtserver.controller;

import com.example.jwtserver.auth.PrincipalDetails;
import com.example.jwtserver.dto.MessageRequestDto;
import com.example.jwtserver.dto.SignupRequestDto;
import com.example.jwtserver.model.User;
import com.example.jwtserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    // 테스트 코드
//    @GetMapping("home")
//    public String home() {
//        return "<h1>home</h1>";
//    }
//
//    @GetMapping("hello")
//    public String hello() {
//        return "<h1>hello</h1>";
//    }

    @PostMapping("/api/v1/signup")
    public MessageRequestDto createUser(@RequestBody SignupRequestDto signupRequestDto){

        System.out.println(signupRequestDto.getUsername());
         // 결과에 따라 ok의 값을 바꿔서 반환.
        /* 백엔드에서 한번 더 중복 체크 */
        String checkName =userService.usernameCheck(signupRequestDto.getUsername());
        String checkEmail = userService.emailCheck(signupRequestDto.getEmail());

        MessageRequestDto messageRequestDto;

        if(checkName.equals("false")){
            System.out.println("false에 걸림");
            messageRequestDto = new MessageRequestDto();
            messageRequestDto.setMessage("usernamefail");
            return messageRequestDto;

        }else if(checkEmail.equals("false")){
            System.out.println("emailFalse에 걸림");
            messageRequestDto = new MessageRequestDto();
            messageRequestDto.setMessage("emailFalse");
            return messageRequestDto;

        }else{
            /* 유저 생성 */
            System.out.println("eslse구문에 걸림");
            User user = userService.createUser(signupRequestDto);
            messageRequestDto = new MessageRequestDto();
            messageRequestDto.setMessage("success");
            return messageRequestDto;
        }
    }



    //user만 접근 가능 //JWT 기능 테스트용
    @GetMapping("/api/v1/user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principal.getUsername());
        return "user";
    }


    /* 유저 아이디 중복 체크 */
    @GetMapping("/api/v1/signup/username/{username}")
    public String userIdCheck(@PathVariable String username){
        return userService.usernameCheck(username);
    }


    /* email중복  */
    @GetMapping("/api/v1/signup/email/{email}")
    public String emailCheck(@PathVariable String email){
        return userService.emailCheck(email);
    }


}
