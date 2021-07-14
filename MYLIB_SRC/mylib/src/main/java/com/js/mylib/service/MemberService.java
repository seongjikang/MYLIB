package com.js.mylib.service;

import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.dto.UpdateMemberRequestDto;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;
import com.js.mylib.dto.JoinMemberRequestDto;
import com.js.mylib.repository.MemberJPARepository;
import com.js.mylib.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final LibraryService libraryService;
	private final MemberJPARepository memberJPARepository;
	public List<MemberInfoDto> findMembers() {
		return memberRepository.findAllMember();
	}

	public List<MemberInfoDto> findMemberByMemberId(Long memberId) {
		List<Member> members= new ArrayList<>();
				members.add(memberRepository.findMemberById(memberId));
		List<MemberInfoDto> result = members.stream()
				.map(m -> new MemberInfoDto(m))
				.collect(Collectors.toList());

		return result;
	}

	public List<MemberInfoDto> findLibraryMemberByLibraryId(Long libraryId) {
		List<Member> LibraryMembers = libraryService.findLibraryById(libraryId).getMembers();
		List<MemberInfoDto> result = LibraryMembers.stream()
				.map(m -> new MemberInfoDto(m))
				.collect(Collectors.toList());
		return result;
	}

	public Long saveMember(JoinMemberRequestDto request) {
		MemberType memberType = null;
		if(request.getType().equals("학생")) {
			memberType = MemberType.STUDENT;
		} else if(request.getType().equals("취준생")) {
			memberType = MemberType.JOB_SEEKER;
		} else if(request.getType().equals("직장인")) {
			memberType = MemberType.WORKER;
		} else {
			memberType = MemberType.ETC;
		}
		Member member = new Member(request.getName(), request.getEmail(), request.getPassword(), memberType);
		memberJPARepository.saveMember(member);
		return member.getId();
	}

	public void deleteMember(Long memberId) {
		memberRepository.deleteMember(memberId);
	}

	public Long updateMember(Long memberId, UpdateMemberRequestDto request) {
		MemberType memberType = null;
		if(request.getType().equals("학생")) {
			memberType = MemberType.STUDENT;
		} else if(request.getType().equals("취준생")) {
			memberType = MemberType.JOB_SEEKER;
		} else if(request.getType().equals("직장인")) {
			memberType = MemberType.WORKER;
		} else {
			memberType = MemberType.ETC;
		}

		memberJPARepository.updateMember(memberId, request, memberType);
		return memberId;

	}

	public Long enterLibrary(Long libraryId, Long memberId) {
		return memberJPARepository.enterLibrary(libraryId, memberId);

	}
}
