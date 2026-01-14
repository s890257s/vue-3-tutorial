package com.eeit.vue3.backend.config.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security 安全配置類別
 * <p>
 *在此類別中配置：
 * 1. CORS (跨來源資源共用) 設定
 * 2. CSRF (跨站請求偽造) 防護關閉 (因為是 REST API)
 * 3. Session 管理策略 (無狀態)
 * 4. HTTP 請求權限控制
 * 5. 添加 JWT 認證過濾器
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CORS: 設定允許的 domain、method、header
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of("*"));
		corsConfiguration.setAllowedHeaders(List.of("*"));

		return http
				// 1. 開啟 CORS (前端 5173 -> 後端 8080，不同 Port 視為跨域)
				.cors(cros -> cros.configurationSource(request -> corsConfiguration))
				// 2. 關閉 CSRF (因為是無狀態 API)
				.csrf(csrf -> csrf.disable())
				// 3. 關閉 Session (改用 JWT)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// 4. 允許所有請求
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				// 5. 插入 JWT Filter (雖然允許所有請求，但還是要解析 Token 才知道是誰)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}