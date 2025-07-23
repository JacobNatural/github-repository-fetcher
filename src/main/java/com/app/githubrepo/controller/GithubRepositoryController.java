package com.app.githubrepo.controller;

import com.app.githubrepo.dto.ResponseRepositoryDto;
import com.app.githubrepo.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GithubRepositoryController {

    private final GithubService githubService;

    @GetMapping("/{username}/repo")
    public ResponseEntity<List<ResponseRepositoryDto>> repo(@PathVariable String username) {
        return ResponseEntity
                .ok()
                .body(githubService.fetchNonForkedRepositoriesWithBranches(username));
    }
}
