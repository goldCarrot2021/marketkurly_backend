package com.example.jwtserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwtserver.auth.PrincipalDetails;
import com.example.jwtserver.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

//스프링 시큐리티에 UsernamePasswordAuthenticationFilter 가 있음
//******** /login 요청해서 username,password를 POST로 전송하면
// UsernamePasswordAuthenticationFilter 필터가 동작함 -> 근데 SecurityConfig에서 formLogin을 안쓰게해서 동작안함
// 필터가 동작하려면 UsernamePasswordAuthenticationFilter를 extends한 JwtAuthenticationFilter를 SecurityConfig에 등록해아함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 메소드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도중");

        // 1. username, password 받아서

        // 2. 정상인지 로그인 시도를 해본다. authenticationManager 로 로그인 시도를 하면
        // PrinciaplDetailsService가 호출되고 loadUserByUsername 가 자동으로 실행됨

        // 3. loadUserByUsername 에서 리턴받은 PrincipalDetails을 세션에 담고 (***세션에 담지 않으면 "권한관리"가 안됨)
        // -> 권한관리 필요없으면 안담아도됨

        // 4. JWT 토큰을 만들어서 응답해주면 됨.

        try{

            ObjectMapper om = new ObjectMapper(); //json을 객체형식으로
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            // 로그인 시도를 위해 토큰을 만든다.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨
            // DB에 있는 username과 password 가 일치한다는 뜻
            // Authentication에는 내가 로그인한 정보가 담김
            Authentication authentication = authenticationManager.authenticate(authenticationToken); //->여기서 exception을 처리 어캐하지

            //=> 로그인이 되었다는 뜻
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            System.out.println(principalDetails.getUser().getUsername()); //값이 있다는 건 로그인이 정상적으로 되었다는 뜻

            //authentication 객체가 session 영역에 저장을 해야하고 그 방법은 return으로 해결
            // return의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는 거임
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리 때문에 session에 넣어주는 것
            return authentication;
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨: 인증이 완료되었다는 것");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //RSA 방식이 아닌 Hash 암호 방식
        // Header, Payload, Signature를 각각 Base64로 인코딩한게 jwtToken이 됨
        // (Header + Payload + 시크릿키)를 HS256(HMAX512+SHA256)로 암호화한게 Signature가 됨
        String jwtToken = JWT.create()
                .withSubject("cos 토큰") //큰 의미 없음
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) //토큰이 언제까지 유효할지 (10분)
                .withClaim("id", principalDetails.getUser().getId()) //claim에는 내가 넣고 싶은거 막 넣어도 됨
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); //시크릿키 값

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken); //헤더에 Authorization으로 담김
    }
}
