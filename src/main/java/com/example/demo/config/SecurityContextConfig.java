package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SecurityContextConfig {

    @PostConstruct
    public void setupSecurityContextStrategy() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        System.out.println("SecurityContextHolder Strategy: " + SecurityContextHolder.getContextHolderStrategy());
    }

}
