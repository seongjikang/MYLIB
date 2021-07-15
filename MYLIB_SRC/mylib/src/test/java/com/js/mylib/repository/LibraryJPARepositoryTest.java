package com.js.mylib.repository;

import com.js.mylib.dto.QLibraryInfoDto;
import com.js.mylib.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static com.js.mylib.entity.QLibrary.*;
import static com.js.mylib.entity.QLibrary.library;
import static com.js.mylib.entity.QMember.*;
import static com.js.mylib.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LibraryJPARepositoryTest {

	@Autowired
	LibraryJPARepository libraryJPARepository;

}