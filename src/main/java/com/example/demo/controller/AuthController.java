package com.example.demo.controller;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 로그인 엔드포인트
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 성공 시 JWT 발급
        return jwtUtil.generateToken(authentication.getName());
    }

    // 회원가입 엔드포인트
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest authRequest) {
        // 비밀번호 암호화 및 사용자 등록
        userDetailsService.saveUser(authRequest.getUsername(), passwordEncoder.encode(authRequest.getPassword()));

        return "User registered successfully!";
    }

    // 현재 인증된 사용자 정보 반환
    @GetMapping("/me")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "Anonymous";
    }

}
