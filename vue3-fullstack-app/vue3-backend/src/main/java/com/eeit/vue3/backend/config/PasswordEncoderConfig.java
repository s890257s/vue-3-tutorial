package com.eeit.vue3.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    /**
     * 建立密碼加密器的 Bean。
     * 此實作來自 Spring Security，底層已實作了強大的 BCrypt 加密機制。
     * 我們只需調用即可，無需自行實作加密演算法。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
