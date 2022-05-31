[![Java CI with Maven](https://github.com/Maksim-k02/Maksim-k-courseSpringBoot/actions/workflows/maven.yml/badge.svg)](https://github.com/Maksim-k02/Maksim-k-courseSpringBoot/actions/workflows/maven.yml)


### Brest Java Course 2021

# Courses project

This application is designed to organize courses and enroll students in these courses. You can view add new courses, view and add students and sort students by date of birth.
More information about the project in the documentation folder.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```
## Run project information ( coverage, dependency, etc. ):

To start Rest server (rest-app module):
```
java -jar ./rest-app/target/rest-app-1.0-SNAPSHOT.jar
The rest application will be available at http://localhost:8087.
```
To start WEB application (web-app module):
```
java -jar ./web-app/target/web-app-1.0-SNAPSHOT.jar
The web application will be available at http://localhost:8084.
```

Swagger UI: http://localhost:8087/swagger-ui.html
The OpenAPI in JSON format: http://localhost:8087/v3/api-docs
```
