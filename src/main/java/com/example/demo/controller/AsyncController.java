package com.example.demo.controller;

import com.example.demo.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
