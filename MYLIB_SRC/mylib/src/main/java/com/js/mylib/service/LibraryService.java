package com.js.mylib.service;

import com.js.mylib.dto.*;
import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.repository.CustomQueryRepository;
import com.js.mylib.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryService {

	private final LibraryRepository libraryRepository;
	private final CustomQueryRepository queryRepository;

	public LibraryInfoDto findLibraryInfoById(Long libraryId) {
		return libraryRepository.findLibraryInfoById(libraryId);
	}

	public List<MemberInfoDto> findLibraryMemberByLibraryId(Long libraryId) {
		List<Member> LibraryMembers = findLibraryById(libraryId).getMembers();
		List<MemberInfoDto> result = LibraryMembers.stream()
				.map(m -> new MemberInfoDto(m))
				.collect(Collectors.toList());
		return result;
	}

	public Library findLibraryById(Long libraryId) {
		return libraryRepository.findLibraryById(libraryId);
	}

    public Long createLibrary(Long memberId, CreateLibraryRequestDto request) {
		Library library = new Library(request.getName(),
									request.getDescription(),
									memberId,
									LocalDateTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
									LocalDateTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
									request.getMemberLimit());
		return queryRepository.createLibrary(memberId, library);
	}

	public Page<LibraryInfoDto> searchLibraries(LibrarySearchConditionDto libraryCondition, Pageable pageable) {
		return libraryRepository.searchLibrariesPage(libraryCondition, pageable);
	}

	@Transactional
	public void deleteLibrary(Long memberId, Long libraryId) {
		exitCurrentLibrary(memberId,libraryId);
		libraryRepository.deleteById(libraryId);
	}

	@Transactional
	public void exitCurrentLibrary(Long memberId, Long libraryId) {
		queryRepository.exitLibrary( memberId, libraryId);
	}

	@Transactional
	public void changeReader(Long memberId, Long libraryId) {
		queryRepository.changeReader(memberId, libraryId);
	}

	public Long enterLibrary(Long libraryId, Long memberId) {
		return queryRepository.enterLibrary(libraryId, memberId);

	}

	@Transactional
	public Long exitLibrary(Long memberId, Long libraryId) {

		LibraryInfoDto libraryInfo = findLibraryInfoById(libraryId);
		List<MemberInfoDto> libraryMember = findLibraryMemberByLibraryId(libraryId);

		if(libraryInfo == null) return -1l;
		boolean isExistMember = false;
		for(int i =0; i<libraryMember.size(); i++) {
			if(libraryMember.get(i).getMemberId() == memberId ) {
				isExistMember = true;
			}
		}

		if(!isExistMember) return -1l;

		// 남은 회원이 한명일 때는 삭제 도서관 삭제
		if(libraryMember.size() == 1) {
			if(memberId == libraryInfo.getReaderId()) {
				System.out.println("여기와야함");
				deleteLibrary(memberId,libraryId);
			}
		} else { //회원이 여러명인데 리더가 아니면
			//리더가 아니면
			if(memberId != libraryInfo.getReaderId()) {
				exitCurrentLibrary( memberId, libraryId);
			} else { //리더라면 나가면서 리더 교체
				changeReader(memberId, libraryId);
			}
		}

		return libraryId;
	}

	public Long updateLibrary(Long libraryId, Long memberId, UpdateLibraryRequestDto updateLibraryRequest) {
		queryRepository.updateLibrary(libraryId, memberId, updateLibraryRequest);
		return libraryId;
	}
}
