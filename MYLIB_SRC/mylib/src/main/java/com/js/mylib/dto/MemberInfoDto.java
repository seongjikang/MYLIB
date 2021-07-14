package com.js.mylib.dto;

import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {
	private Long memberId;
	private String memberName;
	private String email;
	private int accumulateTimeMonth;
	private int accumulateTimeDay;
	private int accumulateTimeYear;
	private MemberType type;
	private String message;

	@QueryProjection
	public MemberInfoDto(Long memberId, String memberName, String email, int accumulateTimeMonth, int accumulateTimeDay, int accumulateTimeYear, MemberType type, String message) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.email = email;
		this.accumulateTimeMonth = accumulateTimeMonth;
		this.accumulateTimeDay = accumulateTimeDay;
		this.accumulateTimeYear = accumulateTimeYear;
		this.type = type;
		this.message = message;
	}

	public MemberInfoDto(Member member) {
		this.memberId = member.getId();
		this.memberName = member.getName();
		this.email = member.getEmail();
		this.accumulateTimeMonth = member.getAccumulateTimeMonth();
		this.accumulateTimeDay = member.getAccumulateTimeDay();
		this.accumulateTimeYear = member.getAccumulateTimeYear();
		this.type = member.getType();
		this.message = member.getMessage();
	}


}
