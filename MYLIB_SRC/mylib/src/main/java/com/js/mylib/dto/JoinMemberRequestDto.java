package com.js.mylib.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class JoinMemberRequestDto {

	@NotEmpty(message = "name is required value")
	private String name;
	@NotEmpty(message = "email is required value")
	private String email;
	@NotEmpty(message = "password is required value")
	private String password;
	@NotEmpty(message = "type is required value")
	private String type;

}
