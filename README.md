### Brest Java Course 2021

# Courses project

This web application to manage electives.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```
## Run project information ( coverage, dependency, etc. ):
```
To start Rest server (rest-app module):

java -jar ./rest-app/target/rest-app-1.0-SNAPSHOT.jar
The rest application will be available at http://localhost:8087.

To start WEB application (web-app module):

java -jar ./web-app/target/web-app-1.0-SNAPSHOT.jar
The web application will be available at http://localhost:8084.