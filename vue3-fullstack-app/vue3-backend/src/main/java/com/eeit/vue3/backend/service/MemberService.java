package com.eeit.vue3.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import com.eeit.vue3.backend.dto.MemberResponseDto;
import com.eeit.vue3.backend.model.mapper.MemberMapper;

@Service
@RequiredArgsConstructor
/**
 * 會員服務層 (Service)
 * <p>
 * 處理會員資料的 CRUD 邏輯。
 */
public class MemberService {

	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;

	/**
	 * 查詢所有會員 (分頁)
	 *
	 * @param pageable 分頁資訊 (頁碼、每頁筆數、排序)
	 * @return 會員分頁結果 (包含 DTO)
	 */
	public Page<MemberResponseDto> findAll(Pageable pageable) {
		return memberRepository.findAll(pageable).map(memberMapper::toMemberResponseDto);
	}

	/**
	 * 新增會員
	 *
	 * @param member 待新增的會員實體
	 * @return 新增後的會員 DTO
	 */
	public MemberResponseDto insert(Member member) {
		Member savedMember = memberRepository.save(member);
		return memberMapper.toMemberResponseDto(savedMember);
	}

	/**
	 * 更新會員資料
	 *
	 * @param id     會員 ID
	 * @param member 包含更新資料的會員實體
	 * @return 更新後的會員 DTO
	 * @throws RuntimeException 當會員不存在時拋出
	 */
	public MemberResponseDto update(Integer id, Member member) {
		Member updatedMember = memberRepository.findById(id).map(existing -> {
			existing.setEmail(member.getEmail());
			existing.setMemberName(member.getMemberName());
			existing.setMemberPhoto(member.getMemberPhoto());
			// Update other fields as necessary, e.g., password if provided
			return memberRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Member not found"));

		return memberMapper.toMemberResponseDto(updatedMember);
	}

	/**
	 * 刪除會員
	 *
	 * @param id 會員 ID
	 */
	public void deleteById(Integer id) {
		memberRepository.deleteById(id);
	}
}
