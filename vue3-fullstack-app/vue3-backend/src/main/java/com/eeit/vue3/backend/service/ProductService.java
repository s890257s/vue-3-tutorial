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

@Service
@RequiredArgsConstructor
/**
 * 商品服務層 (Service)
 * <p>
 * 負責商品查詢邏輯，包含：
 * 1. 取得所有商品
 * 2. 動態條件查詢 (Specification)
 * 3. 分頁查詢
 */
public class ProductService {

	private final ProductMapper productMapper;

	private final ProductRepository productRepository;

	public List<ProductResponse> getAll() {
		return productRepository.findAll().stream()
				.map(productMapper::toDto)
				.toList();
	}

	/**
	 * 根據傳入參數，動態增刪欄位查詢 Product 表格
	 */
	public List<ProductResponse> getByQuery(ProductQuery productQuery) {

		Specification<Product> spce = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (Strings.isNotBlank(productQuery.getName())) {
				Predicate predicate = cb.like(root.get("productName"), "%" + productQuery.getName() + "%");
				predicates.add(predicate);
			}

			if (productQuery.getMin() != null) {
				Predicate predicate = cb.greaterThanOrEqualTo(root.get("price"), productQuery.getMin());
				predicates.add(predicate);
			}

			if (productQuery.getMax() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("price"), productQuery.getMax()));
			}

			if (Strings.isNotBlank(productQuery.getColors())) {
				In<Object> in = cb.in(root.get("color"));
				for (String c : productQuery.getColors().split(",")) {
					System.out.println(c);
					in.value(c);
				}
				predicates.add(in);
			}

			return cb.and(predicates.toArray(new Predicate[0]));

		};

		return productRepository.findAll(spce).stream().map(productMapper::toDto).toList();
	}

	public Page<ProductResponse> getByPaginated(PageQuery dto) {

		/**
		 * 建立分頁物件，依參數順序: </br>
		 * 參數一: page，表示當前頁數，起始值為 0 </br>
		 * 參數二: size，表示每頁顯示筆數 </br>
		 * 參數三: direction，表示排序順序由小到大(升冪 ASC)，或大到小(降冪 DESC) </br>
		 * 參數四: sort，排序目標欄位
		 */
		PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirectionEnum(), dto.getSort());

		Page<Product> pageProducts = productRepository.findAll(pageRequest);

		Page<ProductResponse> pageProductDtos = pageProducts.map(post -> productMapper.toDto(post));

		return pageProductDtos;

	}
}
