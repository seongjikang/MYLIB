package com.js.mylib.controller;

import com.js.mylib.entity.Member;
import com.js.mylib.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
	private final MemberService memberService;

	@GetMapping("/api/v1/members")
	public List<Member> findAllMembers() {
		return memberService.findMembers();
	}
}
