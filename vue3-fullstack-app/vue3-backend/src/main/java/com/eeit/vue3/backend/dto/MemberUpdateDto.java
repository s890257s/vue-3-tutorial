package com.eeit.vue3.backend.dto;

import lombok.Data;

/**
 * 會員更新 DTO
 */
@Data
public class MemberUpdateDto {
    private String email;
    private String memberName;
    private byte[] memberPhoto;
    private Boolean isAdmin;
    // 更新通常不直接改密碼，需另外 API，或是 optional
}
