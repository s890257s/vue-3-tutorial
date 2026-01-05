package com.eeit.vue3.backend.config_full.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

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

	private final HandlerExceptionResolver handlerExceptionResolver;

	private final AuthService authService;

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 從 headers 中取得 Authorization header
		final String authHeader = request.getHeader("Authorization");

		// 若 http 請求的 headers 中不包含 Authorization，或者 Authorization 的格式不正確
		// 則直接交由 Spring Security 驗證規則處理
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 提取 JWT token
		final String jwtToken = authHeader.substring(7);

		// 檢驗 JWT 是否有效，若解析過程中出現錯誤，則會由 jwtUtil 拋出異常。
		// 捕獲異常後，轉發給 GlobalExceptionHandler，並在那定義回應狀態碼。
		try {
			jwtUtil.isTokenValid(jwtToken);
		} catch (Exception e) {
			handlerExceptionResolver.resolveException(request, response, null, e);
			return;
		}

		// 讀取 member 資料
		String memberId = jwtUtil.getSubject(jwtToken);
		LoggedInMember loggedInMember = authService.getMemberById(memberId);

		// 若管理員則給予管理員權限
		Set<SimpleGrantedAuthority> auths = new HashSet<>();
		if (loggedInMember.getIsAdmin()) {
			auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		// UsernamePasswordAuthenticationToken 為 Spring Security 設計用於表示已認證身份的標準物件 </br>
		// 參數一: 認證成功的使用者物件 </br>
		// 參數二: 憑證、密碼等物件，但在 JWT 驗證中不須再額外提供 </br>
		// 參數三: 權限列表物件
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loggedInMember, null, auths);

		// 在此次 context 中儲存驗證成功的 user
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		// 放行此次攔截，繼續執行過濾鍊
		filterChain.doFilter(request, response);
	}

}
