package com.eeit.vue3.backend.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import tw.com.eeit.shop.model.entity.Member;
import tw.com.eeit.shop.model.entity.Product;
import tw.com.eeit.shop.repository.MemberRepository;
import tw.com.eeit.shop.repository.ProductRepository;
import tw.com.eeit.shop.utils.CommonUtil;

@Configuration
public class Initialize implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProductRepository productRepository;

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
		List<Member> members = memberRepository.findAll();
		List<Product> products = productRepository.findAll();

		for (Member m : members) {
			// 若任何一個 member 擁有 photo，則不繼續執行以下所有程式
			if (CommonUtil.isByteArrayNotBlank(m.getMemberPhoto())) {
				return;
			}

			Integer id = m.getMemberId();
			File file = ResourceUtils.getFile("classpath:data\\image\\member\\" + id + ".webp");
			byte[] photo = Files.readAllBytes(file.toPath());
			m.setMemberPhoto(photo);
		}

		for (Product p : products) {
			Integer id = p.getProductId();
			File file = ResourceUtils.getFile("classpath:data\\image\\phone\\" + id + ".webp");
			byte[] photo = Files.readAllBytes(file.toPath());
			p.setProductPhoto(photo);
		}

		memberRepository.saveAll(members);
		productRepository.saveAll(products);
	}
}
