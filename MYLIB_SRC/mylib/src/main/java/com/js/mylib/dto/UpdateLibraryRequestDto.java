package com.js.mylib.dto;

import lombok.Data;

@Data
public class UpdateLibraryRequestDto {
	private String name;
	private String description;
	private String endTime;
	private int memberLimit;
}
