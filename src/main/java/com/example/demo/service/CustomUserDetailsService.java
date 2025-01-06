package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // 사용자 데이터를 메모리에 저장 (실제 환경에서는 DB 연동 필요)
    private final Map<String, UserDetails> users = new ConcurrentHashMap<>();

    public CustomUserDetailsService() {
        // 테스트용 사용자 등록
        users.put("testuser", User.withUsername("testuser")
                .password("{noop}password123") // {noop}은 비밀번호 암호화를 생략 (테스트용)
                .authorities("USER")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }

    // 사용자 등록 메서드 (회원가입에서 사용)
    public void saveUser(String username, String password) {
        users.put(username, User.withUsername(username)
                .password(password) // 실제로는 암호화된 비밀번호를 저장해야 함
                .authorities("USER")
                .build());
    }

}
