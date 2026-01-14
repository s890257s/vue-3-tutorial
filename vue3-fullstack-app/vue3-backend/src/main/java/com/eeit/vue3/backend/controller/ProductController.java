package com.eeit.vue3.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit.vue3.backend.model.dto.PageQuery;
import com.eeit.vue3.backend.model.dto.ProductResponse;
import com.eeit.vue3.backend.model.dto.ProductQuery;
import com.eeit.vue3.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * 商品控制器 (Controller)
 * <p>
 * 提供商品查詢的 API (列表、搜尋、分頁)。
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public Page<ProductResponse> getProducts(
			@ModelAttribute ProductQuery productQuery,
			@ModelAttribute PageQuery pageQuery) {
		return productService.getProducts(productQuery, pageQuery);
	}
}
