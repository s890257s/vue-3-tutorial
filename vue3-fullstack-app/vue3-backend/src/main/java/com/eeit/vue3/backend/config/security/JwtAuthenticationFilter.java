package com.eeit.vue3.backend.config.security;

import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.eeit.vue3.backend.model.dto.LoggedInMember;
import com.eeit.vue3.backend.service.AuthService;
import com.eeit.vue3.backend.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final AuthService authService;

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 從 headers 中取得 Authorization header
		final String authHeader = request.getHeader("Authorization");

		// 若 http 請求的 headers 中不包含 Authorization；或者 headers 中包含
		// Authorization，但格式不合法，則直接交由 Spring Security 處理
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 提取 jwt token
		final String jwtToken = authHeader.substring(7);

		// 讀取 member 資料
		Integer memberId = Integer.valueOf(jwtUtil.getSubject(jwtToken));
		LoggedInMember memberDto = authService.getMemberById(memberId);

		/**
		 * UsernamePasswordAuthenticationToken 為 Spring Security 設計用於表示「已認證身份」的標準物件
		 * 參數一: 認證成功的使用者物件
		 * 參數二: 憑證、密碼等物件，但在 JWT 驗證中不須提供(若是傳統 MVC 表單認證才須提供)
		 * 參數三: 權限列表物件
		 */
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				memberDto, null, null);

		// 在此次 HTTP 請求(context)中，儲存驗證成功的 user
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		// 繼續執行過濾鏈
		filterChain.doFilter(request, response);
	}

}
