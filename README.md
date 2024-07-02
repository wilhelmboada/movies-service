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

1. Code Coverage
   * Add more unit tests and the Jacoco plugin to verify code coverage every time changes are made, ensuring a minimum percentage of code is covered.
   * Reason: Requires additional time to write more tests and to set up and integrate Jacoco into the existing build process.
2. Asynchronous Processing
   * Depending on the requirement, use WebFlux or another library to handle asynchronous flows for better performance and scalability.
   * Reason: Current requirements do not demand high concurrency or asynchronous processing.
3. Security 
   * Enhance API security using Spring Security. 
   * Reason: The API is currently in a development environment with restricted access, making immediate security enhancements unnecessary. 
4. Visual Documentation 
   * Create visual architecture diagrams to illustrate the system's structure, providing a clear overview of the system components and their interactions.
   * Reason: Requires additional tools and time to create comprehensive diagrams.
5. Sequence Diagrams
   * Use sequence diagrams to showcase the flow of operations, helping to understand the interactions between different parts of the system and improving communication among team members.
   * Reason: Similar to visual architecture diagrams, this requires additional time and resources to produce.