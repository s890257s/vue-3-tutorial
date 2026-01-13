package com.eeit.vue3.backend.dto;

import lombok.Data;

@Data
public class MemberResponseDto {
    private Integer memberId;
    private String email;
    private String memberName;
    private byte[] memberPhoto;
    private Boolean isAdmin;
}
