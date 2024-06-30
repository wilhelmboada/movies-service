# Movies API

This API provides a list of directors who have directed more movies than a specified threshold, sorted alphabetically.

## Swagger Documentation

You can explore the API documentation using Swagger UI at:

* `http://localhost:8080/swagger-ui/index.html`


## Endpoints

The API exposes the following endpoint:

* `GET  /api/directors`: Returns a list of directors who have directed more movies than the specified threshold, sorted alphabetically.

## Main Frameworks and Libraries

| Feature                      | Library                                         |
|------------------------------|-------------------------------------------------|
| Build tool                   | Gradle                                          |
| HTTP server                  | Spring Boot Starter Web                         | 

## Environment setup

### Prerequisites

Ensure you have the following tools installed:

1. Java 17
2. Gradle

### Starting the Applications

Run the application using Gradle:

`./gradlew bootRun`

### Improvements

Here's a roadmap for enhancing the Movies API:

1. Increase Test Coverage
   * Expand unit test coverage.

2. Integration Tests
   * Introduce more integration tests to ensure seamless collaboration between modules.

3. Add Diagrams
   * Create visual architecture diagrams to illustrate the system's structure.
   * Use sequence diagrams to showcase the flow of operations.

4. REST Services Validations
   * Strengthen input validations for REST services.