package com.js.mylib.service;

import com.js.mylib.dto.CreateLibraryRequestDto;
import com.js.mylib.entity.Library;
import com.js.mylib.repository.LibraryJPARepository;
import com.js.mylib.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryService {

	private final LibraryRepository libraryRepository;
	private final LibraryJPARepository libraryJPARepository;

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
}
