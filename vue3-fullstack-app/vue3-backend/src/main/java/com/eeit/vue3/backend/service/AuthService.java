package com.eeit.vue3.backend.service;

import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eeit.vue3.backend.model.dto.EmailAndPassword;
import com.eeit.vue3.backend.model.dto.LoggedInMember;
import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.model.mapper.MemberMapper;
import com.eeit.vue3.backend.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;

	/**
	 * 根據 ID 查找登入使用者資訊
	 */
	public LoggedInMember getMemberById(Integer id) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("找不到使用者 id: " + id));
		LoggedInMember loggedInMember = memberMapper.memberToLoggedInMember(member);
		return loggedInMember;
	}

	/**
	 * 根據 ID 查找登入使用者資訊，多載方法，讓呼叫者無須自己實作轉型
	 */
	public LoggedInMember getMemberById(String id) {
		return getMemberById(Integer.parseInt(id));
	}

	/**
	 * 取得當前登入的使用者
	 */
	public LoggedInMember getLoggedInMember() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal == null) {
			throw new RuntimeException("使用者尚未登入");
		}

		LoggedInMember loggedInMember = (LoggedInMember) principal;

		return loggedInMember;
	}

	public LoggedInMember login(EmailAndPassword ep) {

		// 找不到 member 表示帳號(email) 打錯
		Member member = memberRepository.findByEmail(ep.getEmail())
				.orElseThrow(() -> new RuntimeException("找不到使用者 email: " + ep.getEmail()));

		// 密碼不符合
		boolean incorrectPassword = !Objects.equals(ep.getPassword(), member.getPassword());

		if (incorrectPassword) {
			throw new RuntimeException("密碼錯誤");
		}

		// 建立登入成功物件
		return memberMapper.memberToLoggedInMember(member);
	}

}
