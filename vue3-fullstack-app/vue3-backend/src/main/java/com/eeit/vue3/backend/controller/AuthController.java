package com.eeit.vue3.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.model.dto.LoginRequest;
import com.eeit.vue3.backend.service.AuthService;

/**
 * 認證控制器 (Controller)
 * <p>
 * 提供登入相關的 API。
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * 會員登入
	 * <p>
	 * 驗證 email 和密碼，成功後回傳 JWT Token。
	 *
	 * @param loginRequest 登入請求 (包含 email 和 password)
	 * @return 登入回應 (包含 JWT Token 和會員資訊)
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {

		LoginResponse loginResponse = authService.login(loginRequest);

		return ResponseEntity.ok().header("Authorization", "Bearer " + loginResponse.getToken())
				.body(loginResponse);
	}

}
