package com.eeit.vue3.backend.dto;

import lombok.Data;

/**
 * 會員資料 DTO
 * <p>
 * 通常用於接收註冊或更新會員資料的請求。
 */
@Data
public class MemberDto {
    private String email;
    private String password;
    private String memberName;
    private byte[] memberPhoto;
    private Boolean isAdmin;
}
