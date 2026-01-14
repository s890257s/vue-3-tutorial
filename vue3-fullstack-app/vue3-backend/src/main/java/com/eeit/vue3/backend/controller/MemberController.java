package com.eeit.vue3.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.service.MemberService;
import com.eeit.vue3.backend.dto.MemberCreateDto;
import com.eeit.vue3.backend.dto.MemberUpdateDto;
import com.eeit.vue3.backend.dto.MemberResponseDto;
import com.eeit.vue3.backend.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

/**
 * 會員控制器 (Controller)
 * <p>
 * 提供會員 CRUD 的 API。
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    /**
     * 取得會員列表 (分頁)
     *
     * @param page 頁碼 (預設為 1)
     * @param size 每頁筆數 (預設為 10)
     * @return 會員分頁結果
     */
    @GetMapping
    public Page<MemberResponseDto> getMembers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable;
        if (size <= 0) {
            pageable = Pageable.unpaged();
        } else {
            int pageNo = Math.max(0, page - 1);
            pageable = PageRequest.of(pageNo, size);
        }
        return memberService.findAll(pageable);
    }

    /**
     * 新增會員
     *
     * @param memberDto 會員建立資料 (包含密碼)
     * @return 新增後的會員資料
     */
    @PostMapping
    public MemberResponseDto insert(@RequestBody MemberCreateDto memberDto) {
        Member member = memberMapper.toMember(memberDto);
        return memberService.insert(member);
    }

    /**
     * 更新會員資料
     *
     * @param id        會員 ID
     * @param memberDto 會員更新資料 (不包含密碼)
     * @return 更新後的會員資料
     */
    @PutMapping("/{id}")
    public MemberResponseDto update(@PathVariable Integer id, @RequestBody MemberUpdateDto memberDto) {
        Member member = memberMapper.toMember(memberDto);
        return memberService.update(id, member);
    }

    /**
     * 刪除會員
     *
     * @param id 會員 ID
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        memberService.deleteById(id);
    }
}
