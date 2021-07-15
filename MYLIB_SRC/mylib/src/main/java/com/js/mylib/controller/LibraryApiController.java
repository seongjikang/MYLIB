package com.js.mylib.controller;

import com.js.mylib.dto.CreateLibraryRequestDto;
import com.js.mylib.dto.LibraryInfoDto;
import com.js.mylib.dto.LibrarySearchConditionDto;
import com.js.mylib.service.LibraryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/v1/libraries/find/{libraryId}")
    public LibraryInfoDto findLibraryByLibraryId(@PathVariable Long libraryId) {
        return libraryService.findLibraryInfoById(libraryId);
    }

    @GetMapping("/api/v1/libraries")
    public Page<LibraryInfoDto> searchLibraries(LibrarySearchConditionDto libraryCondition, Pageable pageable) {
        return libraryService.searchLibraries(libraryCondition, pageable);
    }

    @Data
    static class LibraryResponse {
        private Long id;
        public LibraryResponse(Long id) {
            this.id = id;
        }
    }
}
