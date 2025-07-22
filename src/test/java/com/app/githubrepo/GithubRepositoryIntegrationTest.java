package com.app.githubrepo;

import com.app.githubrepo.dto.ResponseBranchDto;
import com.app.githubrepo.dto.ResponseRepositoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubRepositoryIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Given:
     * - Repository with no commits
     * - Repository with one branch and one commit
     * - Repository with one branch and multiple commits
     * - Repository with three branches and one or more commits
     * - Forked repository is excluded
     */

    @Test
    public void testFetchRepositoriesHappyPath() {

        // when
        var response =
                restTemplate.exchange(
                        "/fetch-repository-test/repo",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ResponseRepositoryDto>>() {
                        }).getBody();

        // then
        assertThat(response)
                .hasSize(4)
                .extracting(ResponseRepositoryDto::login)
                .allMatch(login -> login.equals("fetch-repository-test"));

        assertThat(response)
                .extracting(ResponseRepositoryDto::repositoryName)
                .containsExactlyInAnyOrder(
                        "test-branch-1-commit-1",
                        "test-branch-1-commit-n",
                        "test-branch-3-commit-n",
                        "test-empty")
                .doesNotContain("test-forked-repo");

        assertThat(response)
                .anySatisfy(rep ->
                        assertThat(rep.branches())
                                .isEmpty()
                ).anySatisfy(rep ->
                        assertThat(rep.branches())
                                .isNotEmpty()
                );

        assertThat(response)
                .filteredOn(rep -> rep.repositoryName().equals("test-branch-3-commit-n"))
                .allSatisfy(rep ->
                        assertThat(rep.branches())
                                .extracting(ResponseBranchDto::branchName)
                                .containsExactlyInAnyOrder("main", "dev", "feature")
                );

        assertThat(response)
                .filteredOn(rep -> !rep.branches().isEmpty())
                .allSatisfy(rep ->
                        assertThat(rep.branches())
                                .extracting(ResponseBranchDto::sha)
                                .isNotEmpty()
                );
    }
}

