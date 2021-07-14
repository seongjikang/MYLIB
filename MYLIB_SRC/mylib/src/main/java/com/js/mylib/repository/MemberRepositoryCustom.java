package com.js.mylib.repository;

import com.js.mylib.dto.MemberInfoDto;

import java.util.List;

public interface MemberRepositoryCustom {

	List<MemberInfoDto> findAllMember();
	void deleteMember(Long memberId);
}
