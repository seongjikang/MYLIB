package com.js.mylib.controller;

import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.dto.JoinMemberRequestDto;
import com.js.mylib.dto.UpdateMemberRequestDto;
import com.js.mylib.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
	private final MemberService memberService;

	@PostMapping("/api/v1/members/join")
	public MemberResponse join(@RequestBody @Valid JoinMemberRequestDto request) {
		Long id= memberService.saveMember(request);
		return new MemberResponse(id);
	}

	@GetMapping("/api/v1/members")
	public List<MemberInfoDto> findAllMembers() {
		return memberService.findMembers();
	}

	@GetMapping("/api/v1/members/find/{memberId}")
	public MemberInfoDto findMemberByMemberId(@PathVariable Long memberId) {
		return memberService.findMemberByMemberId(memberId);
	}

	@PutMapping("/api/v1/members/update/{memberId}")
	public MemberResponse updateMember(@PathVariable Long memberId, @RequestBody @Valid UpdateMemberRequestDto request) {
		Long id= memberService.updateMember(memberId, request);
		return new MemberResponse(id);
	}

	@Data
	static class MemberResponse {
		private Long id;
		public MemberResponse(Long id) {
			this.id = id;
		}
	}


}