package com.eeit.vue3.backend.dto;

import lombok.Data;

/**
 * 購物車項目請求 DTO
 * <p>
 * 用於接收前端傳來的購物車新增/修改請求資料。
 */
@Data
public class CartItemRequestDto {

	private Integer memberId;
	private Integer productId;
	private Integer qty;

}
