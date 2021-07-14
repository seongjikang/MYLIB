package com.js.mylib.controller;

import com.js.mylib.dto.CreateLibraryRequestDto;
import com.js.mylib.service.LibraryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LibraryApiController {
    private final LibraryService libraryService;

    @PostMapping("/api/v1/libraries/create/{memberId}")
    public LibraryResponse createLibrary(@PathVariable Long memberId, @RequestBody @Valid CreateLibraryRequestDto request) {
        Long id= libraryService.createLibrary(memberId, request);
        return new LibraryResponse(id);
    }

    @Data
    static class LibraryResponse {
        private Long id;
        public LibraryResponse(Long id) {
            this.id = id;
        }
    }
}
