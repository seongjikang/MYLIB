package com.js.mylib.service;

import com.js.mylib.dto.LibraryInfoDto;
import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.dto.UpdateMemberRequestDto;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;
import com.js.mylib.dto.JoinMemberRequestDto;
import com.js.mylib.repository.LibraryJPARepository;
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
	private final LibraryJPARepository libraryJPARepository;
	public List<MemberInfoDto> findMembers() {
		return memberRepository.findAllMember();
	}

	public MemberInfoDto findMemberByMemberId(Long memberId) {
//		List<Member> members= new ArrayList<>();
//				members.add(memberRepository.findMemberById(memberId));
//		List<MemberInfoDto> result = members.stream()
//				.map(m -> new MemberInfoDto(m))
//				.collect(Collectors.toList());
		return memberRepository.findMemberInfoById(memberId);

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

	@Transactional
	public Long exitLibrary(Long memberId, Long libraryId) {

		LibraryInfoDto libraryInfo = libraryService.findLibraryInfoById(libraryId);
		List<MemberInfoDto> libraryMember = findLibraryMemberByLibraryId(libraryId);

		// 남은 회원이 한명일 때는 삭제 도서관 삭제
		if(libraryMember.size() == 1) {
			if(memberId == libraryInfo.getReaderId()) {
				libraryService.deleteLibrary(libraryId);
			}
		} else { //회원이 여러명인데 리더가 아니면
			//리더가 아니면
			if(memberId != libraryInfo.getReaderId()) {
				libraryService.exitLibrary( memberId, libraryId);
			} else { //리더라면 나가면서 리더 교체
				System.out.println("들어옴??");
				libraryService.changeReader(memberId, libraryId);
			}
		}

		return memberId;
	}
}
