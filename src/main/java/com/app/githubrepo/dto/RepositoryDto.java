package com.app.githubrepo.dto;

public record RepositoryDto(OwnerDto owner, String name, boolean fork){
}
