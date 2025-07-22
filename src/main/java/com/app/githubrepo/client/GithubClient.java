package com.app.githubrepo.client;

import com.app.githubrepo.dto.BranchDto;
import com.app.githubrepo.dto.RepositoryDto;

import java.util.List;

public interface GithubClient {
    List<RepositoryDto> fetchUserRepos(String username);
    List<BranchDto> fetchBranches(String username, String repository);
}
