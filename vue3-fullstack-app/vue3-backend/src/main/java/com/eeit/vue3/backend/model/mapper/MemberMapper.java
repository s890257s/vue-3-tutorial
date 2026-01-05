package com.eeit.vue3.backend.model.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.eeit.vue3.backend.model.dto.LoggedInMember;
import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.utils.CommonUtil;
import com.eeit.vue3.backend.utils.JwtUtil;

@Component
public class MemberMapper {

	private final JwtUtil jwtUtil;

	public MemberMapper(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	public LoggedInMember memberToLoggedInMember(Member entity) {

		LoggedInMember dto = new LoggedInMember();
		BeanUtils.copyProperties(entity, dto);

		String base64Image = CommonUtil.getBase64Image(entity.getMemberPhoto());
		dto.setEmail(base64Image);

		String role = entity.getIsAdmin() ? "ADMIN" : "USER";

		dto.setToken(jwtUtil.generateToken(entity.getMemberId().toString(), role));

		return dto;
	}
}
