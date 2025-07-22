
# GitHub Repository Fetcher
![Java Version](https://img.shields.io/badge/Java-21-blue)
![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.5-green)

A simple Spring Boot application that fetches public GitHub repositories for a given user, excluding forks,
and returns repository details along with branch names and last commit SHAs.

## Features

- Fetch all public repositories for a GitHub user (excluding forked repositories).
- For each repository, return:
   - Repository name
   - Owner login
   - Branch names with the last commit SHA
- Returns 404 response with a JSON error message if the GitHub user does not exist.
- Integration test covering the main happy path scenario.

## GitHub API Endpoints Used


- **List user repositories**  
  `GET https://api.github.com/users/{username}/repos`  
  [GitHub Docs](https://docs.github.com/en/rest/repos/repos#list-repositories-for-a-user)
  

- **List branches**  
  `GET https://api.github.com/repos/{owner}/{repo}/branches`  
  [GitHub Docs](https://docs.github.com/en/rest/branches/branches#list-branches)

## Technologies

- Java 21
- Spring Boot 3.5
- Spring Web (RestTemplate)
- JUnit 5
- AssertJ

## API Endpoints

### GET http://localhost:8080/{username}/repo

Fetches repositories for the given GitHub username.

#### Response example (HTTP 200):

```json
[
  {
    "login": "fetch-repository-test",
    "repositoryName": "test-branch-1-commit-1",
    "branches": [
      {
        "branchName": "main",
        "sha": "abcd1234"
      }
    ]
  },
  {
    "login": "fetch-repository-test",
    "repositoryName": "test-empty",
    "branches": []
  }
]
```

#### Error response example (HTTP 404):

```json
{
  "status": 404,
  "message": "User not found"
}
```
## Requirements

Before running the application, make sure the following tools and environment are set up:

- **Java 21+** – Required to run Spring Boot 3.5 applications.
- **Maven 3.8+** – For building and managing the project.
- **Internet connection** – Necessary for communicating with the GitHub API (no authentication required).

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/jacobnatural/github-repository-fetcher.git
cd github-repository-fetcher
```

2. Build the project with Maven (or your build tool):

```bash
mvn clean package -DskipTests
```

3. Run the application:

```bash
mvn spring-boot:run
```

4. Access the endpoint (replace `{username}` with a valid GitHub login), e.g.:

```
http://localhost:8080/{username}/repo
```

## Test Setup

Integration tests use a dedicated GitHub test account with pre-configured repositories:

- Test account: [fetch-repository-test](https://github.com/fetch-repository-test)
- Repository structure:
   - `test-empty` - empty repository
   - `test-branch-1-commit-1` - 1 branch, 1 commit
   - `test-branch-1-commit-n` - 1 branch, multiple commits
   - `test-branch-3-commit-n` - 3 branches (main, dev, feature)
   - `test-forked-repo` - forked repository (excluded in results)

A single integration test covers the main use case (happy path) and verifies:

- Filtering out forked repositories
- Repository and branch details correctness
- Handling of empty branches list
- No mocks are used (direct calls to GitHub API)

Run tests using:

```bash
mvn test
```

## Notes

- The app uses the official [GitHub REST API v3](https://docs.github.com/en/rest).
- Uses GitHub's public API without authentication (no API key needed)
- Pagination is not handled (all repositories fetched at once).
- Global exception handling provides consistent error responses.
- No WebFlux or reactive programming is used (per task requirement).
