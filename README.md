# Book Management API
Book Management API for performing CRUD operation on books.

## Table of Contents
+ [About](#about)
+ [Getting Started](#gettingstarted)
+ [Usage](#usage)

## About <a name = "about" id = "about"></a>
The purpose of this project is to perform CRUD operation of books. In addition,
it can be used to retrieve list of books by title name.

## Getting Started <a name = "gettingstarted" id = "gettingstarted"></a>
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them.

```
Java 11
Gradle 8.8
Spring 3.2.7
```

### Installing

A step by step series of examples that tell you how to get a development env running.

In order to locally run in development mode
```
1) ./gradlew clean build
```
## Execution
### Running in development mode
```
1) ./gradlew clean build
2) java -jar ./build/libs/management-0.0.1-SNAPSHOT.jar
```

### Testing
```
./gradlew test
```

### Running Application through Docker Compose
Running through Docker Compose
```
1) docker compose up (docker-compose up)
2) docker run -p 8080:8080 management/management
``````

## Usage <a name = "usage" id = "usage"></a>

In order to test the application go to Swagger link http://localhost:8080/swagger-ui/index.html and test APIs.
In addition, you can test the APIs through Postman as well.
