package com.eeit.vue3.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit.vue3.backend.model.dto.PageQuery;
import com.eeit.vue3.backend.model.dto.ProductDto;
import com.eeit.vue3.backend.model.dto.ProductQuery;
import com.eeit.vue3.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/all")
	public List<ProductDto> getAll() {
		return productService.getAll();
	}

	@GetMapping("/query")
	public List<ProductDto> getByQuery(@ModelAttribute ProductQuery query) {
		return productService.getByQuery(query);
	}

	@GetMapping("/page")
	public Page<ProductDto> getByPaginated(@ModelAttribute PageQuery query) {
		return productService.getByPaginated(query);
	}
}
