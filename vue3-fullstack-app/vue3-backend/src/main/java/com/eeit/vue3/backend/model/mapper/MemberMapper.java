package com.eeit.vue3.backend.model.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.model.entity.Member;

@Component
public class MemberMapper {

	public LoginResponse memberToLoginResponse(Member entity) {

		LoginResponse dto = new LoginResponse();
		BeanUtils.copyProperties(entity, dto);

		// String base64Image = CommonUtil.getBase64Image(entity.getMemberPhoto());
		// 如果前端需要 Base64 圖片，請在 DTO 新增欄位，不要覆蓋 Email

		return dto;
	}
}
