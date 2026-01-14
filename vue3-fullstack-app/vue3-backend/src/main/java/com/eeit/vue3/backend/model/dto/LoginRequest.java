package com.eeit.vue3.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 登入請求 DTO
 * <p>
 * 封裝前端傳來的登入資訊 (Email 與密碼)。
 */
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "email 不得為空")
    private String email;

    @NotBlank(message = "password 不得為空")
    private String password;

}
