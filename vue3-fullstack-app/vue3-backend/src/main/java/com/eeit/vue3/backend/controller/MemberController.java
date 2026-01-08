package com.eeit.vue3.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public Page<Member> getMembers(
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

    @PostMapping
    public Member insert(@RequestBody Member member) {
        return memberService.insert(member);
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Integer id, @RequestBody Member member) {
        return memberService.update(id, member);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        memberService.deleteById(id);
    }
}
