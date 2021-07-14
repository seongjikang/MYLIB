package com.js.mylib.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "readerId", "description", "startTime", "endTime", "limit"})
@Table(name = "library_tb")
public class Library {
	@Id @GeneratedValue
	@Column(name = "library_id")
	private Long id;

	private String name;

	private Long readerId;

	private String description;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "library")
	private List<Member> members = new ArrayList<>();

	private int memberLimit;

	public Library(String name, String description, Long readerId, LocalDateTime startTime, LocalDateTime endTime, int memberLimit) {
		this.name = name;
		this.readerId = readerId;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.memberLimit = memberLimit;
	}


}
