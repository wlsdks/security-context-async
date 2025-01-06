package com.example.demo.controller;

import com.example.demo.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class AsyncController {

    private final AsyncService asyncService;

    @GetMapping("/test/async")
    public String testAsync() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Main Thread에서 SecurityContext 확인
        if (authentication != null) {
            System.out.println("Main Thread User: " + authentication.getName());
        } else {
            System.out.println("Main Thread: No SecurityContext");
        }

        // @Async 메서드 호출
        asyncService.executeAsyncTask();

        // Default Async (SecurityContext 미전파)
        asyncService.executeWithDefaultAsync();

        // Security Async (SecurityContext 전파)
        asyncService.executeWithSecurityAsync();

        return "Async Test Triggered!";
    }


    @GetMapping("/test/small-async")
    public String testAsync(@RequestParam String username, @RequestParam String requestId) {
        // SecurityContext에 사용자 정보 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        ));
        SecurityContextHolder.setContext(context);

        // 현재 사용자 확인
        System.out.printf("[Main Thread] Request: %s, Thread: %s, User: %s%n",
                requestId, Thread.currentThread().getName(), username);

        // 비동기 작업 실행
        asyncService.executeAsyncTask(requestId);

        return "Async Task Triggered!";
    }

}
