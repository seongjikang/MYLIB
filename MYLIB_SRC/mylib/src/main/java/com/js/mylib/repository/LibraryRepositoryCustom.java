package com.js.mylib.repository;

import com.js.mylib.dto.LibraryInfoDto;
import com.js.mylib.dto.LibrarySearchConditionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LibraryRepositoryCustom {
	LibraryInfoDto findLibraryInfoById(Long libraryId);
	Page<LibraryInfoDto> searchLibrariesPage(LibrarySearchConditionDto libraryCondition, Pageable pageable);
}
