package com.eeit.vue3.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "email 不得為空")
    private String email;

    @NotBlank(message = "password 不得為空")
    private String password;

}
