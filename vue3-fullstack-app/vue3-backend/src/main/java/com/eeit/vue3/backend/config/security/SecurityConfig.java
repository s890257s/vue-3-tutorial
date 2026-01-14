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

	/**
	 * 配置 Security Filter Chain (安全過濾鏈)
	 * <p>
	 * 這是 Spring Security 執行的核心。每一個請求都會經過這個鏈。
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CORS: 設定允許的 domain、method、header
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("*")); // 允許所有來源 (開發階段方便)
		corsConfiguration.setAllowedMethods(List.of("*")); // 允許所有 HTTP 方法 (GET, POST, PUT, DELETE...)
		corsConfiguration.setAllowedHeaders(List.of("*")); // 允許所有 Header

		return http
				// 1. 開啟 CORS (因為前端在 5173，後端在 8080，不同 Port 視為跨域，必須開啟)
				.cors(cros -> cros.configurationSource(request -> corsConfiguration))
				// 2. 關閉 CSRF (因為我們使用 JWT 機制，不是基於 Cookie/Session，所以不需要防範 CSRF)
				.csrf(csrf -> csrf.disable())
				// 3. 設定 Session 管理策略為「無狀態」(STATELESS)
				// 這意味著 Server 不會保存使用者的 Session，每次請求都必須攜帶 Token
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// 4. 設定路由權限 (目前為了開發方便，允許所有請求 permitAll)
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				// 5. 將自定義的 JWT 過濾器加入 Filter Chain
				// 加在 UsernamePasswordAuthenticationFilter 之前，確保先驗證 Token
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}