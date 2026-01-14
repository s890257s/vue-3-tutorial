package com.eeit.vue3.backend.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eeit.vue3.backend.dto.MemberResponseDto;
import com.eeit.vue3.backend.dto.MemberCreateDto;
import com.eeit.vue3.backend.dto.MemberUpdateDto;
import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.model.entity.Member;

@Mapper(componentModel = "spring")
/**
 * 會員資料轉換器 (Mapper)
 * <p>
 * 使用 MapStruct 自動生成實作類別。
 * 負責將 Entity 轉換為 DTO，或將 DTO 轉換為 Entity。
 */
public interface MemberMapper {

	@Mapping(target = "token", ignore = true)
	LoginResponse toLoginResponse(Member entity);

	MemberResponseDto toMemberResponseDto(Member entity);

	// String base64Image = CommonUtil.getBase64Image(entity.getMemberPhoto());
	// 如果前端需要 Base64 圖片，請在 DTO 新增欄位，不要覆蓋 Email

	@Mapping(target = "memberId", ignore = true)
	Member toMember(MemberCreateDto dto);

	@Mapping(target = "memberId", ignore = true)
	@Mapping(target = "password", ignore = true) // Update doesn't handle password
	Member toMember(MemberUpdateDto dto);
}
