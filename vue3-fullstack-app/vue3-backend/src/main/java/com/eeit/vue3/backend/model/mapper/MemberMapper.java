package com.eeit.vue3.backend.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eeit.vue3.backend.dto.MemberResponseDto;
import com.eeit.vue3.backend.dto.MemberDto;
import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.model.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {

	@Mapping(target = "token", ignore = true)
	LoginResponse toLoginResponse(Member entity);

	MemberResponseDto toMemberResponseDto(Member entity);

	// String base64Image = CommonUtil.getBase64Image(entity.getMemberPhoto());
	// 如果前端需要 Base64 圖片，請在 DTO 新增欄位，不要覆蓋 Email

	@Mapping(target = "memberId", ignore = true)
	Member toMember(MemberDto dto);
}
