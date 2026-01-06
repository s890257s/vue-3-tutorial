package com.eeit.vue3.backend.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.model.entity.Product;
import com.eeit.vue3.backend.repository.MemberRepository;
import com.eeit.vue3.backend.repository.ProductRepository;
import com.eeit.vue3.backend.utils.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Initialize implements ApplicationListener<ContextRefreshedEvent> {

	private final MemberRepository memberRepository;

	private final ProductRepository productRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			initPhotos();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void initPhotos() throws IOException {
		// 1. Pre-check: 若資料庫已存在圖片，則視為已初始化，直接跳過。
		boolean memberInitDone = memberRepository.existsByMemberPhotoIsNotNull();
		boolean productInitDone = productRepository.existsByProductPhotoIsNotNull();

		if (memberInitDone && productInitDone) {
			log.info("初始化檢查: 資料庫中已有圖片資料，跳過初始化流程。");
			return;
		}

		// 2. Member Initialization
		if (!memberInitDone) {
			List<Member> members = memberRepository.findAll();
			for (Member m : members) {
				// 雙重檢查：若個別已有圖則跳過
				if (CommonUtil.isByteArrayNotBlank(m.getMemberPhoto())) {
					continue;
				}

				Integer id = m.getMemberId();
				// 使用 ClassPathResource 確保 JAR 檔兼容性，並使用正斜線 /
				ClassPathResource imgRes = new ClassPathResource("initialization_data/image/member/" + id + ".webp");

				if (imgRes.exists()) {
					try (InputStream is = imgRes.getInputStream()) {
						byte[] photo = StreamUtils.copyToByteArray(is);
						m.setMemberPhoto(photo);
					}
				} else {
					log.warn("找不到會員圖片: {}", id);
				}
			}
			memberRepository.saveAll(members);
			log.info("會員圖片初始化完成");
		}

		// 3. 商品圖片初始化
		if (!productInitDone) {
			List<Product> products = productRepository.findAll();
			for (Product p : products) {
				if (CommonUtil.isByteArrayNotBlank(p.getProductPhoto())) {
					continue;
				}

				Integer id = p.getProductId();
				ClassPathResource imgRes = new ClassPathResource("initialization_data/image/phone/" + id + ".webp");

				if (imgRes.exists()) {
					try (InputStream is = imgRes.getInputStream()) {
						byte[] photo = StreamUtils.copyToByteArray(is);
						p.setProductPhoto(photo);
					}
				} else {
					log.warn("找不到商品圖片: {}", id);
				}
			}
			productRepository.saveAll(products);
			log.info("商品圖片初始化完成");
		}
	}
}
