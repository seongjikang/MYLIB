package com.js.mylib.dto;

import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class LibraryInfoDto {
    private Long libraryId;
    private String libraryName;
    private Long readerId;
    private String readerName;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private long dDay;

    @QueryProjection
    public LibraryInfoDto(Long libraryId, String libraryName, Long readerId, String readerName, String description, LocalDateTime startTime, LocalDateTime endTime, long dDay) {
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.readerId = readerId;
        this.readerName = readerName;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dDay = dDay;
    }

    public LibraryInfoDto(Library library, Member member) {
        this.libraryId = library.getId();
        this.libraryName = library.getName();
        this.readerId = member.getId();
        this.readerName = member.getName();
        this.description = library.getDescription();
        this.startTime = library.getStartTime();
        this.endTime = library.getEndTime();
        this.dDay = ChronoUnit.DAYS.between(startTime, endTime);
    }
}
