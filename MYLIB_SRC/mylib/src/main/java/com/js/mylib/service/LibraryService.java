package com.js.mylib.service;

import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.repository.LibraryRepository;
import com.js.mylib.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryService {

	private final LibraryRepository libraryRepository;

	public Library findLibraryById(Long libraryId) {
		return libraryRepository.findLibraryById(libraryId);
	}
}
