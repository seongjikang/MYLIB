package com.js.mylib.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Library {
	@Id @GeneratedValue
	@Column(name = "library_id")
	private Long id;

	private String name;

	private String description;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "library")
	private List<Member> members = new ArrayList<>();

	private int limit;

	public Library(String name, String description, LocalDateTime startTime, LocalDateTime endTime, int limit) {
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.limit = limit;
	}


}
