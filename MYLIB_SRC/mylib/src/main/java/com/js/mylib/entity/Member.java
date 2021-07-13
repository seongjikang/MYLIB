package com.js.mylib.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "userName", "birth", "sex", "memberType"})
public class Member {
	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;

	@Column(name="email" , unique=true)
	private String email;
	private String password;


	private int accumulateTimeMonth;
	private int accumulateTimeDay;
	private int accumulateTimeYear;

	@Enumerated(EnumType.STRING)
	private MemberType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="library_id")
	private Library library;

	public Member(String name, String email, String password, MemberType type) {
		this.name = name;
		this.email = email;
		this.password= password;
		this.type = type;
		this.accumulateTimeDay = 0;
		this.accumulateTimeMonth = 0;
		this.accumulateTimeYear = 0;
	}

	public void setLibrary(Library library) {
		this.library = library;
		library.getMembers().add(this);
	}

}
