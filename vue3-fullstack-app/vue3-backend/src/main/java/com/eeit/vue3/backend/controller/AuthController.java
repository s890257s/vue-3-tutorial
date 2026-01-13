package com.eeit.vue3.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.model.dto.LoginRequest;
import com.eeit.vue3.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
/**
 * 認證控制器 (Controller)
 * <p>
 * 提供登入相關的 API 接口。
 */
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {

		LoginResponse loginResponse = authService.login(loginRequest);

		return ResponseEntity.ok().header("Authorization", "Bearer " + loginResponse.getToken())
				.body(loginResponse);
	}

}
