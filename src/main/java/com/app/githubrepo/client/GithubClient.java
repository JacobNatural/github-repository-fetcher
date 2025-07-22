package com.app.githubrepo.client;

import com.app.githubrepo.dto.BranchDto;
import com.app.githubrepo.dto.RepositoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubClient {
    private final RestTemplate restTemplate;

    public List<RepositoryDto> fetchUserRepos(String username) {
            return restTemplate.exchange(
                    String.format("https://api.github.com/users/%s/repos", username),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RepositoryDto>>() {
                    }
            ).getBody();
    }

    public List<BranchDto> fetchBranches(String username, String repository){
        return restTemplate.exchange(
                String.format("https://api.github.com/repos/%s/%s/branches", username, repository),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BranchDto>>() {}
        ).getBody();
    }
}
