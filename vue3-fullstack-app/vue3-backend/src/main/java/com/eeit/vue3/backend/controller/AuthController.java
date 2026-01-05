package com.eeit.vue3.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit.vue3.backend.model.dto.EmailAndPassword;
import com.eeit.vue3.backend.model.dto.LoggedInMember;
import com.eeit.vue3.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoggedInMember> login(@RequestBody EmailAndPassword emailAndPasswordDto) {

		LoggedInMember loggedInMemberDto = authService.login(emailAndPasswordDto);

		return ResponseEntity.ok().header("Authorization", "Bearer " + loggedInMemberDto.getToken())
				.body(loggedInMemberDto);
	}

}
