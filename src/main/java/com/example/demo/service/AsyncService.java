package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsyncService {

    @Async
    public void executeAsyncTask() {
        String username = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "No SecurityContext";

        System.out.println("[@Async] Current User: " + username);
    }

    @Async("asyncExecutor")
    public void executeWithDefaultAsync() {
        String username = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "No SecurityContext";

        System.out.println("[Default Async] Current User: " + username);
    }

    @Async("smallAsyncExecutor")
    public void executeAsyncTask(String requestId) {
        String username = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "No SecurityContext";

        System.out.printf(
                "[Async Task] Request: %s, Thread: %s, SecurityContext: %s, User: %s%n",
                requestId,
                Thread.currentThread().getName(),
                SecurityContextHolder.getContext(),
                username
        );
    }


    @Async("securityAsyncExecutor")
    public void executeWithSecurityAsync() {
        String username = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "No SecurityContext";

        System.out.println("[Security Async] Current User: " + username);
    }

}
