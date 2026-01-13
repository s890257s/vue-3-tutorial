package com.eeit.vue3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eeit.vue3.backend.model.entity.Product;

/**
 * 商品資料存取層 (Repository)
 * <p>
 * 繼承 JpaSpecificationExecutor 以支援動態查詢 (Specification)。
 */
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    boolean existsByProductPhotoIsNotNull();

}
