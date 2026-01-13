package com.eeit.vue3.backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.model.dto.LoginRequest;
import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.model.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.eeit.vue3.backend.repository.MemberRepository;
import com.eeit.vue3.backend.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	/**
	 * 根據 ID 查找登入使用者資訊
	 */
	public LoginResponse getMemberById(Integer id) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("找不到使用者 id: " + id));
		LoginResponse loginResponse = memberMapper.toLoginResponse(member);
		return loginResponse;
	}

	/**
	 * 根據 ID 查找登入使用者資訊，多載方法，讓呼叫者無須自己實作轉型
	 */
	public LoginResponse getMemberById(String id) {
		return getMemberById(Integer.parseInt(id));
	}

	/**
	 * 取得當前登入的使用者
	 */
	public LoginResponse getLoggedInMember() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal == null) {
			throw new RuntimeException("使用者尚未登入");
		}

		LoginResponse loginResponse = (LoginResponse) principal;

		return loginResponse;
	}

	public LoginResponse login(LoginRequest loginRequest) {
		String unifiedErrorMsg = "帳號或密碼錯誤";

		Member member = memberRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new RuntimeException(unifiedErrorMsg));

		boolean incorrectPassword = !passwordEncoder.matches(loginRequest.getPassword(), member.getPassword());

		if (incorrectPassword) {
			throw new RuntimeException(unifiedErrorMsg);
		}

		// 轉型成 DTO
		LoginResponse dto = memberMapper.toLoginResponse(member);

		// 生成並注入 Token
		String role = member.getIsAdmin() ? "ADMIN" : "USER";
		String token = jwtUtil.generateToken(member.getMemberId().toString(), role);
		dto.setToken(token);

		return dto;
	}

}
