package com.app.githubrepo.service;

import com.app.githubrepo.client.GithubClient;
import com.app.githubrepo.dto.RepositoryDto;
import com.app.githubrepo.dto.ResponseBranchDto;
import com.app.githubrepo.dto.ResponseRepositoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;

    public List<ResponseRepositoryDto> fetchNonForkedRepositoriesWithBranches(String username) {
        return githubClient
                .fetchUserRepos(username)
                .stream()
                .filter(repo -> !repo.fork())
                .map(repo -> toResponseDtoWithBranches(repo))
                .toList();
    }

    private ResponseRepositoryDto toResponseDtoWithBranches(RepositoryDto repositoryDto) {
        var branches = githubClient
                .fetchBranches(repositoryDto.owner().login(), repositoryDto.name())
                .stream()
                .map(branch -> new ResponseBranchDto(branch.name(), branch.commit().sha()))
                .toList();

        return new ResponseRepositoryDto(
                repositoryDto.owner().login(),
                repositoryDto.name(),
                branches
        );
    }
}
