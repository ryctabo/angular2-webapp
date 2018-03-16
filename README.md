# Arpia Backend ![Build Status](https://nativapps.visualstudio.com/_apis/public/build/definitions/68e8cfce-ec95-4033-bb10-c9812bc2de6a/7/badge)
This project takes care of the interactions in applications into the Arpia Platform, providing a REST web
application where it offers resources and endpoins for its use.

## Getting Started
To start you need install some dependencies on your local machine.

### Global dependencies
For this project, you need the following dependencies:
* [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html "Click for download") (7 or latest)
* [Maven](https://maven.apache.org/download.cgi "Click for download") (3.3.9 or latest)
* [Apache Tomcat](http://tomcat.apache.org/ "Click to visit official page") (8.0.39 or latest)

For the data storage is being used [MySQL](http://https://www.mysql.com/), this must be have the following
configuration:
* Database name: *arpia_database*
* Username: *arpia_user*
* Password: *Arpia2016*

## Development time
The project use [Maven](http://maven.apache.org) as a manager and to automate tasks. Use `compile` maven task 
to download local dependencies and compile the project and use `clean` to clean it.

For clean and compile the project:
```
mvn clean compile
```

For documentation is using [RAML](http://raml.org/) in its version [0.8](https://github.com/raml-org/raml-spec/blob/master/versions/raml-08/raml-08.md "Click for read specifications")
where it is compiled an HTML through [raml2html](https://github.com/raml2html/raml2html), it depended that we
can install with NPM using the following command:
```
npm install raml2html
```
For building:
```
raml2html docs/documentation.raml > src/main/webapp/index.html
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
All development rights are reserved by [<img src="http://nativapps.co/nativapps/wp-content/uploads/2016/04/nativapps-logo-share.png" width="80" align="middle"/>](http://nativapps.co/).