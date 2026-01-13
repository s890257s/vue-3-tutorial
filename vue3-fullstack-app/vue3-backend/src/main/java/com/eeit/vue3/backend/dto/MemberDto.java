package com.eeit.vue3.backend.dto;

import lombok.Data;

@Data
public class MemberDto {
    private String email;
    private String password;
    private String memberName;
    private byte[] memberPhoto;
    private Boolean isAdmin;
}
