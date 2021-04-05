package com.example.jwtserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwtserver.auth.PrincipalDetails;
import com.example.jwtserver.model.User;
import com.example.jwtserver.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 서버에서 클라이언트의 요청으로부터 온 JWT 토큰을 검증한다.
// 서버는 요청으로부터 온 JWT 토큰의 Header와 Payload 그리고 서버에서 알고있는 시크릿키 값을 더해서 HS256(HMAX512+SHA256)으로 똑같이 암호화해
// 요청으로부터 온 JWT 토큰의 Signature 값과 대조해본다.
// 값이 동일하다면 인증이 완료됐다는 것.

// ******* 인증/권한 이 필요한 경우 타는 filter
// 시큐리티가 filter를 가지고 있는데 그 필터 중 BasicAuthenticationFilter라는 것이 있음
// *******권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되어있음 ********
// 만약 권한이나 인증이 필요한 주소가 아니라면 위 필터를 거치지 않음
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    //인증이나 권한이 필요한 주소요청이 있을때 해당 필터를 타게됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨");

        String jwtHeader = request.getHeader("Authorization"); // Request 요청의 헤더에 있는 Authorization을 확인
        System.out.println("jwtHeader: " + jwtHeader);

        //header가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        //JWT 토큰을 검증해서 정상적인 사용자인지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        // "서명(signature)" 과정(Signature 값 비교 과정)을 거쳐서 username을 뽑아냄
        String username =
                JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();

        //서명(signature)이 정상적으로 됨
        if(username != null) {
            System.out.println("서명 검증이 정상적으로 됨");
            User userEntity = userRepository.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);
        }
    }
}
