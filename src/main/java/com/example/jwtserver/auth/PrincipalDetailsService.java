package com.example.jwtserver.auth;

import com.example.jwtserver.model.User;
import com.example.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// http://localhost:8080/login 요청때 동작함 -> 근데 SecuriyConfig에서 formLogin을 안쓰기로 했으므로 동작안함 -> 따로 동작하게 필터(UsernamePasswordAuthenticationFilter)를 설정해야함
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 실행됨
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
//        return new PrincipalDetails(userEntity); //Optional<User> 가 아니고 그냥 User일때
        return user.map(PrincipalDetails::new).orElseThrow(() -> new UsernameNotFoundException("데이터베이스에서 찾을 수 없습니다.")); // 입력받은 username에 해당하는 사용자가 있다면, PrincipalDetails 객체를 생성한다.


    }
}
