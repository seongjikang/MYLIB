package com.js.mylib.service;

import com.js.mylib.dto.LibraryInfoDto;
import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.dto.UpdateMemberRequestDto;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;
import com.js.mylib.dto.JoinMemberRequestDto;
import com.js.mylib.repository.CustomQueryRepository;
import com.js.mylib.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final CustomQueryRepository customQueryRepository;
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
		customQueryRepository.saveMember(member);
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

		customQueryRepository.updateMember(memberId, request, memberType);
		return memberId;

	}

//	public Long enterLibrary(Long libraryId, Long memberId) {
//		return customQueryRepository.enterLibrary(libraryId, memberId);
//
//	}
//
//	@Transactional
//	public Long exitLibrary(Long memberId, Long libraryId) {
//
//		LibraryInfoDto libraryInfo = libraryService.findLibraryInfoById(libraryId);
//		List<MemberInfoDto> libraryMember = findLibraryMemberByLibraryId(libraryId);
//
//		if(libraryInfo == null) return -1l;
//		boolean isExistMember = false;
//		for(int i =0; i<libraryMember.size(); i++) {
//			if(libraryMember.get(i).getMemberId() == memberId ) {
//				isExistMember = true;
//			}
//		}
//
//		if(!isExistMember) return -1l;
//
//		// 남은 회원이 한명일 때는 삭제 도서관 삭제
//		if(libraryMember.size() == 1) {
//			if(memberId == libraryInfo.getReaderId()) {
//				libraryService.deleteLibrary(memberId,libraryId);
//			}
//		} else { //회원이 여러명인데 리더가 아니면
//			//리더가 아니면
//			if(memberId != libraryInfo.getReaderId()) {
//				libraryService.exitLibrary( memberId, libraryId);
//			} else { //리더라면 나가면서 리더 교체
//				libraryService.changeReader(memberId, libraryId);
//			}
//		}
//
//		return memberId;
//	}
}
