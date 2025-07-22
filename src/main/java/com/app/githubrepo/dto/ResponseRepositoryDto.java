package com.app.githubrepo.dto;

import java.util.List;

public record ResponseRepositoryDto(String login, String repositoryName, List<ResponseBranchDto> branches) {
}
