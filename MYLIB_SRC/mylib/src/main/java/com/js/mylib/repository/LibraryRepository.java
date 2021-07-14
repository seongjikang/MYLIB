package com.js.mylib.repository;

import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
	Library findLibraryById(Long id);
}
