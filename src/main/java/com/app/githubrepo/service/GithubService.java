package com.app.githubrepo.service;

import com.app.githubrepo.dto.ResponseRepositoryDto;

import java.util.List;

public interface GithubService {
    List<ResponseRepositoryDto> fetchNonForkedRepositoriesWithBranches(String username);
}
