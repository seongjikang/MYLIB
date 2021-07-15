package com.js.mylib.service;

import com.js.mylib.dto.CreateLibraryRequestDto;
import com.js.mylib.dto.LibraryInfoDto;
import com.js.mylib.dto.LibrarySearchConditionDto;
import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.repository.LibraryJPARepository;
import com.js.mylib.repository.LibraryRepository;
import com.js.mylib.repository.MemberJPARepository;
import com.js.mylib.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryService {

	private final LibraryRepository libraryRepository;
	private final LibraryJPARepository libraryJPARepository;

	public LibraryInfoDto findLibraryInfoById(Long libraryId) {
		return libraryRepository.findLibraryInfoById(libraryId);
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
		return libraryJPARepository.createLibrary(memberId, library);
	}

	public Page<LibraryInfoDto> searchLibraries(LibrarySearchConditionDto libraryCondition, Pageable pageable) {
		return libraryRepository.searchLibrariesPage(libraryCondition, pageable);
	}

	@Transactional
	public void deleteLibrary(Long libraryId) {
		libraryRepository.deleteById(libraryId);
	}

	@Transactional
	public void exitLibrary(Long memberId, Long libraryId) {
		libraryJPARepository.exitLibrary( memberId, libraryId);
	}

	@Transactional
	public void changeReader(Long memberId, Long libraryId) {
		libraryJPARepository.changeReader(memberId, libraryId);
	}
}
