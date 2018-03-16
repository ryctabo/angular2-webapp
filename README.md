# Java Jersey JAX-RS ![Build Status](https://nativapps.visualstudio.com/_apis/public/build/definitions/68e8cfce-ec95-4033-bb10-c9812bc2de6a/7/badge)
This project takes care of the interactions in applications into the Arpia Platform, providing a REST web
application where it offers resources and endpoins for its use.

## Getting Started
To start you need install some dependencies on your local machine.

### Global dependencies
For this project, you need the following dependencies:
* [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html "Click for download") (7 or latest)
* [Maven](https://maven.apache.org/download.cgi "Click for download") (3.3.9 or latest)
* [Apache Tomcat](http://tomcat.apache.org/ "Click to visit official page") (8.0.39 or latest)

## Development time
The project use [Maven](http://maven.apache.org) as a manager and to automate tasks. Use `compile` maven task 
to download local dependencies and compile the project and use `clean` to clean it.

For clean and compile the project:
```
mvn clean compile
```

### Running the tests
The tests in this project are make with JUnit. You need use the following maven task:
```
mvn test
```

## Deployment
The project use [Maven](http://maven.apache.org) to build it, you need use the following maven task:
```
mvn package
```

----------------------
MIT License!
