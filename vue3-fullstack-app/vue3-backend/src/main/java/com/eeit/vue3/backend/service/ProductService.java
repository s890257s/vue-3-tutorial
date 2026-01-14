package com.eeit.vue3.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Predicate;

import com.eeit.vue3.backend.model.dto.PageQuery;
import com.eeit.vue3.backend.model.dto.ProductResponse;
import com.eeit.vue3.backend.model.dto.ProductQuery;
import com.eeit.vue3.backend.model.entity.Product;
import com.eeit.vue3.backend.model.mapper.ProductMapper;
import com.eeit.vue3.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * 商品服務層 (Service)
 * <p>
 * 負責商品查詢邏輯，包含：
 * 1. 取得所有商品
 * 2. 動態條件查詢 (Specification)
 * 3. 分頁查詢
 */
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductMapper productMapper;

	private final ProductRepository productRepository;



	/**
	 * 整合查詢 (列表 + 搜尋 + 分頁)
	 */
	public Page<ProductResponse> getProducts(ProductQuery productQuery, PageQuery pageQuery) {

		// 1. 建立 Specification (搜尋條件)
		Specification<Product> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (productQuery != null) {
				if (Strings.isNotBlank(productQuery.getName())) {
					predicates.add(cb.like(root.get("productName"), "%" + productQuery.getName() + "%"));
				}

				if (productQuery.getMin() != null) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("price"), productQuery.getMin()));
				}

				if (productQuery.getMax() != null) {
					predicates.add(cb.lessThanOrEqualTo(root.get("price"), productQuery.getMax()));
				}

				if (Strings.isNotBlank(productQuery.getColors())) {
					In<Object> in = cb.in(root.get("color"));
					for (String c : productQuery.getColors().split(",")) {
						in.value(c.trim());
					}
					predicates.add(in);
				}
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		// 2. 建立 PageRequest (分頁與排序)
		PageRequest pageRequest = PageRequest.of(
				pageQuery.getPage(),
				pageQuery.getSize(),
				pageQuery.getDirectionEnum(),
				pageQuery.getSort());

		// 3. 查詢並轉換
		return productRepository.findAll(spec, pageRequest)
				.map(productMapper::toDto);
	}
}
