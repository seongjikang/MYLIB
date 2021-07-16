package com.js.mylib.controller;

import com.js.mylib.dto.*;
import com.js.mylib.service.LibraryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/api/v1/libraries/find/library/member/{libraryId}")
    public List<MemberInfoDto> findLibraryMemberByLibraryId(@PathVariable Long libraryId) {
        return libraryService.findLibraryMemberByLibraryId(libraryId);
    }

    @GetMapping("/api/v1/libraries")
    public Page<LibraryInfoDto> searchLibraries(LibrarySearchConditionDto libraryCondition, Pageable pageable) {
        return libraryService.searchLibraries(libraryCondition, pageable);
    }

    @PutMapping("/api/v1/libraries/update/library/{libraryId}/member/{memberId}")
    public LibraryResponse updateLibrary(@PathVariable Long libraryId, @PathVariable Long memberId, @RequestBody @Valid UpdateLibraryRequestDto updateLibraryRequest) {
        Long id = libraryService.updateLibrary(libraryId, memberId, updateLibraryRequest);
        return new LibraryResponse(id);
    }

    @DeleteMapping("/api/v1/libraries/exit/library/{libraryId}/member/{memberId}")
    public LibraryResponse exitLibrary(@PathVariable Long libraryId, @PathVariable Long memberId) {
        Long id = libraryService.exitLibrary(memberId, libraryId);
        return new LibraryResponse(id);
    }

    @PostMapping("/api/v1/libraries/enter/library/{libraryId}/member/{memberId}")
    public LibraryResponse enterLibrary(@PathVariable Long libraryId, @PathVariable Long memberId) {
        Long id = libraryService.enterLibrary(libraryId, memberId);
        return new LibraryResponse(id);
    }

    @Data
    static class LibraryResponse {
        private Long id;
        private String message;
        public LibraryResponse(Long id) {
            this.id = id;
        }
    }
}
