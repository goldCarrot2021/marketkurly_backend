package com.example.jwtserver.controller;

import com.example.jwtserver.auth.PrincipalDetails;
import com.example.jwtserver.dto.SignupRequestDto;
import com.example.jwtserver.model.User;
import com.example.jwtserver.repository.UserRepository;
import com.example.jwtserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
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
    @ResponseBody
    public String createUser(@RequestBody SignupRequestDto userRequestDto){
        /* 유저 생성*/
        User user = userService.createUser(userRequestDto);

        String ok="";
        if(user==null){ /* user객체가 비어있다면 = 유저가 생성 안됐다면*/
            ok="false"; /* false를 프론트단 에 넘겨줌 */

        }else { /* user객체가 비어있지않다면 = 유저가 생성 됐다면 */
            ok="true"; /* true를 프론트 단에 넘겨줌 */
        }

        return ok;
    }



//    //user만 접근 가능 //JWT 기능 테스트용
//    @GetMapping("/api/v1/user")
//    public String user(Authentication authentication) {
//        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("authentication : " + principal.getUsername());
//        return "user";
//    }


    // 장바구니 api url 확인
    @GetMapping("/api/v1/cart")
    public String cart() {
        return "<h1>cart</h1>";
    }

    /* 유저 아이디 중복 체크 */
    @GetMapping("api/v1/signup/users/{userId}")
    @ResponseBody
    public String userIdCheck(@PathVariable String username){
        return userService.usernameCheck(username);
    }


    /* email중복  */
    @GetMapping("/api/v1/signup/users")
    @ResponseBody
    public String emailCheck(@RequestParam String email){
        return userService.emailCheck(email);
    }

}
