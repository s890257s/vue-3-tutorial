package com.eeit.vue3.backend.controller;



import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit.vue3.backend.model.dto.PageQuery;
import com.eeit.vue3.backend.dto.ProductCreateDto;
import com.eeit.vue3.backend.dto.ProductResponseDto;
import com.eeit.vue3.backend.dto.ProductUpdateDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/**
	 * 取得商品列表 (分頁 + 搜尋)
	 *
	 * @param productQuery 商品查詢條件
	 * @param pageQuery    分頁條件
	 * @return 商品分頁結果
	 */
	@GetMapping
	public Page<ProductResponseDto> getProducts(
			@ModelAttribute ProductQuery productQuery,
			@ModelAttribute PageQuery pageQuery) {

		return productService.getProducts(productQuery, pageQuery);
	}

	/**
	 * 取得單一商品
	 *
	 * @param id 商品 ID
	 * @return 商品詳細資料
	 */
	@GetMapping("/{id}")
	public ProductResponseDto getProductById(@PathVariable Integer id) {
		return productService.getProductById(id);
	}

	/**
	 * 新增商品
	 *
	 * @param dto 商品建立資料
	 * @return 新增後的商品詳細資料
	 */
	@PostMapping
	public ProductResponseDto createProduct(@RequestBody ProductCreateDto dto) {
		return productService.createProduct(dto);
	}

	/**
	 * 更新商品
	 *
	 * @param id  商品 ID
	 * @param dto 商品更新資料
	 * @return 更新後的商品詳細資料
	 */
	@PutMapping("/{id}")
	public ProductResponseDto updateProduct(@PathVariable Integer id, @RequestBody ProductUpdateDto dto) {
		return productService.updateProduct(id, dto);
	}

	/**
	 * 刪除商品
	 *
	 * @param id 商品 ID
	 */
	@DeleteMapping("/{id}")
	public void removeProduct(@PathVariable Integer id) {
		productService.removeProduct(id);
	}
}
