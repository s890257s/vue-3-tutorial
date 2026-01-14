package com.eeit.vue3.backend.dto;

import lombok.Data;

/**
 * 會員建立 DTO
 */
@Data
public class MemberCreateDto {
    private String email;
    private String password;
    private String memberName;
    private byte[] memberPhoto;
    private Boolean isAdmin;
}
