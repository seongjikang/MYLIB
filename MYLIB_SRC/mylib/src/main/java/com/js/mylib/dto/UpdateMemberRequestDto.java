package com.js.mylib.dto;

import lombok.Data;

@Data
public class UpdateMemberRequestDto {
	private String name;
	private String message;
	private int accumulateTimeMonth;
	private int accumulateTimeDay;
	private int accumulateTimeYear;
	private String memberImage;
	private String type;
}
