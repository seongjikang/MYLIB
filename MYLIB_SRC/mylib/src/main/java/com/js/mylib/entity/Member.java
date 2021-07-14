package com.js.mylib.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "email", "password", "message", "accumulateTimeMonth", "accumulateTimeDay", "accumulateTimeYear", "type"})
@Table(name = "member_tb")
public class Member {
	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;

	@Column(name="email" , unique=true)
	private String email;
	private String password;

	private String message;

	private int accumulateTimeMonth;
	private int accumulateTimeDay;
	private int accumulateTimeYear;

	private String memberImage;

	@Enumerated(EnumType.STRING)
	private MemberType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="library_id")
	private Library library;

	@CreatedDate
	private LocalDateTime createdDate;

	public Member(String name, String email, String password, MemberType type) {
		this.name = name;
		this.email = email;
		this.password= password;
		this.type = type;
		this.message = "";
		this.accumulateTimeDay = 0;
		this.accumulateTimeMonth = 0;
		this.accumulateTimeYear = 0;
	}

	public Member(String name, String email, String password, MemberType type, Library library) {
		this.name = name;
		this.email = email;
		this.password= password;
		this.type = type;
		this.message = "";
		this.accumulateTimeDay = 0;
		this.accumulateTimeMonth = 0;
		this.accumulateTimeYear = 0;
		if(library !=null) {
			setLibrary(library);
		}

	}

	public void setLibrary(Library library) {
		this.library = library;
		library.getMembers().add(this);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAccumulateTimeMonth(int accumulateTimeMonth) {
		this.accumulateTimeMonth = accumulateTimeMonth;
	}

	public void setAccumulateTimeDay(int accumulateTimeDay) {
		this.accumulateTimeDay = accumulateTimeDay;
	}

	public void setAccumulateTimeYear(int accumulateTimeYear) {
		this.accumulateTimeYear = accumulateTimeYear;
	}

	public void setMemberImage(String memberImage) {
		this.memberImage = memberImage;
	}

	public void setType(MemberType type) {
		this.type = type;
	}

}
