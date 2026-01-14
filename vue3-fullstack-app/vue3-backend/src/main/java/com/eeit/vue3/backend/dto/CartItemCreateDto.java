package com.eeit.vue3.backend.dto;

import lombok.Data;

/**
 * 購物車項目新增 DTO
 */
@Data
public class CartItemCreateDto {

	private Integer productId;
	private Integer qty;

}
