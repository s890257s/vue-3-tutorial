package com.eeit.vue3.backend.dto;

import lombok.Data;

@Data
/**
 * 會員回應 DTO
 * <p>
 * 用於回傳會員資訊給前端 (不包含密碼等敏感資訊)。
 */
public class MemberResponseDto {
    private Integer memberId;
    private String email;
    private String memberName;
    private byte[] memberPhoto;
    private Boolean isAdmin;
}
