package com.example.jwtserver.config;

import com.example.jwtserver.jwt.JwtAuthenticationFilter;
import com.example.jwtserver.jwt.JwtAuthorizationFilter;
import com.example.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.BCException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터(SecurityConfig)가 스프링 필터 체인에 등록됨
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                // 해당 설정 없으면  h2-console 확인 안됨
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다.

                .and()
                .addFilter(corsFilter) //crossOrigin 정책을 벗어나게(모든 요청을 허용하겠다). 인증을 하기위해 시큐리티 필터에 등록
                .formLogin().disable() //스프링 시큐리티가 제공하는 form 태그 로그인 안한다.
                .httpBasic().disable() //header의 Authorization에 id,pw를 들고 가는 방식 - disable 하고
                // Bearer방식(header의 Authorization에 토큰을 넣는 방식)을 쓸것임
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticationManager 가 파라미터로 들어가야함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository)) //AuthenticationManager 가 파라미터로 들어가야함
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                //.antMatchers("/api/v1/cart/**").authenticated() //이런식으로 인증된 사용자만 접근할 수 있다.
                .antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER')")
                .anyRequest().permitAll();
    }
}
