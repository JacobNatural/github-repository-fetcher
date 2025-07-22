package com.app.githubrepo.service.impl;

import com.app.githubrepo.client.impl.GithubClientImpl;
import com.app.githubrepo.dto.RepositoryDto;
import com.app.githubrepo.dto.ResponseBranchDto;
import com.app.githubrepo.dto.ResponseRepositoryDto;
import com.app.githubrepo.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {
    private final GithubClientImpl githubClientImpl;

    public List<ResponseRepositoryDto> fetchNonForkedRepositoriesWithBranches(String username) {
        return githubClientImpl
                .fetchUserRepos(username)
                .stream()
                .filter(repo -> !repo.fork())
                .map(repo -> toResponseDtoWithBranches(repo))
                .toList();
    }

    private ResponseRepositoryDto toResponseDtoWithBranches(RepositoryDto repositoryDto) {
        var branches = githubClientImpl
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
