package com.js.mylib.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "userName", "age"})
public class Member {
	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String userName;
	private int age;

	public Member(String userName, int age) {
		this.userName = userName;
		this.age = age;
	}
}
