package com.eeit.vue3.backend.config.security;

import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.service.AuthService;
import com.eeit.vue3.backend.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

/**
 * JWT 認證過濾器
 * <p>
 * 繼承 {@link OncePerRequestFilter} 確保在一次請求中只會執行一次。
 * 主要功能：
 * 1. 攔截所有 HTTP 請求
 * 2. 檢查 Header 中是否有有效的 JWT Token
 * 3. 若 Token 有效，則解析使用者資訊並設定到 Spring Security 的 SecurityContext 中
 */
@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final AuthService authService;
	private final JwtUtil jwtUtil;

	/**
	 * 核心過濾邏輯
	 *
	 * @param request     HTTP 請求
	 * @param response    HTTP 回應
	 * @param filterChain 過濾鏈 (用於將請求傳遞給下一個過濾器)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1. 從 headers 中取得 Authorization header
		final String authHeader = request.getHeader("Authorization");

		// 2. 檢查 Header 是否存在且格式正確 (必須以 "Bearer " 開頭)
		// 若不符合，代表沒有攜帶 Token 或格式錯誤，直接放行給下一個 Filter (Spring Security 會處理未授權的情況)
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 3. 提取 JWT Token (去掉 "Bearer " 前綴)
		final String jwtToken = authHeader.substring(7);

		// 4. 解析 Token 並認證
		// 若 Token 簽名驗證失敗或過期，getSubject() 會拋出例外，這裡會由全域例外處理捕捉 (或 filter 內部捕捉)
		Integer memberId = Integer.valueOf(jwtUtil.getSubject(jwtToken));
		
		// 根據 ID 查出會員資料 (確認會員確實存在)
		LoginResponse memberDto = authService.getMemberById(memberId);

		// 5. 建立 Spring Security 認證物件 (Authentication)
		/**
		 * UsernamePasswordAuthenticationToken: Spring Security 用來代表「已認證使用者」的物件
		 * - principal: 使用者主體 (這裡放 memberDto)
		 * - credentials: 密碼 (JWT 驗證不需要，放 null)
		 * - authorities: 權限列表 (目前先放 null)
		 */
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				memberDto, null, null);

		// 6. 將認證物件存入 SecurityContext
		// 這樣後續的 Controller 就可以透過 @AuthenticationPrincipal 取得使用者資訊
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		// 7. 繼續執行下一個過濾器
		filterChain.doFilter(request, response);
	}

}
