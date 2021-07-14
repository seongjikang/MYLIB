package com.js.mylib.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateLibraryRequestDto {
    @NotEmpty(message = "name is required value")
    private String name;
    private String description;

    @NotEmpty(message = "startTime is required value")
    private String startTime;

    @NotEmpty(message = "endTime is required value")
    private String endTime;

    @NotEmpty(message = "memberLimit is required value")
    private int memberLimit;

}
