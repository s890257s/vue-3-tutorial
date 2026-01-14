package com.eeit.vue3.backend.dto;

import lombok.Data;

@Data
public class ProductCreateDto {
    private String name;
    private Integer price;
    private String color;
    private byte[] photo;
}
