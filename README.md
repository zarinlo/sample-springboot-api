[![CircleCI](https://circleci.com/gh/zarinlo/sample-springboot-api/tree/master.svg?style=svg)](https://circleci.com/gh/zarinlo/sample-springboot-api/tree/master)
[![Docker Automated build](https://img.shields.io/docker/automated/zarinlo/springboot-api?color=blue&logo=docker)](https://hub.docker.com/r/zarinlo/springboot-api)

# Sample Spring Boot API using Remote MongoDB Atlas
- [Topics Covered](#topics-covered)
- [Guides](#guides)
- [Software](#software)
- [Codebase](#codebase)
- [Setup and Connect to the Database](#setup-and-connect-to-the-database)
    * [Option 1 - Setup and Run Local MongoDB](#option-2---setup-and-run-local-mongodb)
    * [Option 2 - Setup and Connect to Remote MongoDB](#option-3---setup-and-connect-to-remote-mongodb)
- [How the Database Populates](#how-the-database-populates)
- [Run API via IDE](#run-api-via-ide)
- [Run API via Command Line](#run-api-via-command-line)
    * [Windows](#windows)
    * [MacOS](#macos)
- [Swagger](#swagger)
- [Spring Actuator Endpoints](#spring-actuator-endpoints)

# Topics Covered

This sample project aims to teach you: 

- How to structure a Java Spring Boot API
- How to design an API contract (i.e. set HTTP response codes, develop routes, etc)
- What the following components are responsible for: Controller, Service, Repository 
- How to utilize a remote MongoDB cluster
- How to set up Swagger / Open API spec, an Interface Description Language for describing RESTful APIs

# Guides

- [How to design and develop this Java Spring Boot API](https://zarin.io/codelabs/springboot-api/#0)

# Software

| Software | Version | Required | MacOS Guide | Notes
| --- |---------| --- | --- | --- |
| [OpenJDK](https://www.oracle.com/java/technologies/javase-downloads.html) | 17.0.4  | true | [How to setup openjdk via Homebrew](https://johnathangilday.com/blog/macos-homebrew-openjdk/) | If you are using an older version of openjdk (minimum v11+), you can still run this project by either setting **VM options** in the Run Config or appending the following to the bash command below: `-Djdk.tls.client.protocols=TLSv1.2`
| [Apache Maven](https://maven.apache.org/download.cgi) | 3.8.6   | true | [Install maven via Homebrew](https://formulae.brew.sh/formula/maven) | [Understanding Apache Maven - The Series](https://cguntur.me/2020/05/20/understanding-apache-maven-the-series/) 
| [MongoDB](https://www.mongodb.com/download-center#community) | 6.0     | false | [Install mongodb-community via Homebrew](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x) | Use an embedded version of MongoDB. More info under the database related sections.

# Codebase

- **Controllers**: Manages all the REST calls and status codes 
- **Services**: The business logic layer that handles any manipulation of data required 
- **Repositories**: Uses a Java Persistence API (JPA) that analyzes all the methods defined by an interface 
and automatically generates queries from the method names, in order to simplify the connection to the database from the Service layer
- **Configs**: Sets up the configurations for the REST calls, web security, Swagger documentation, etc
- **Models**: Defines the structure of all the data objects 

# Setup and Connect to the Database

### Option 1 - Setup and Run Local MongoDB

- MacOS Guide: [Install mongodb-community via Homebrew](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x)
- Windows Guide: [Install mongodb via MSI package](https://www.simplilearn.com/tutorials/mongodb-tutorial/install-mongodb-on-windows)

### Option 2 - Setup and Connect to Remote MongoDB

- [Read the section on how to setup and connect to MongoDB Atlas](https://faun.pub/setup-a-circleci-pipeline-for-a-containerized-spring-boot-app-93045fa060de)

# How the Database Populates

- The database (i.e. MongoDB Atlas cluster) is populated once you initialize the backend.
- The API used to populate the database is the **Latest Stock API**: https://rapidapi.com/suneetk92/api/latest-stock-price/
- The `RestTemplateConfig.java` makes the connection to the API.
- The code snippet that populates the database and refreshes the data once every minute is in `StockServiceImpl.java` as: 

    ```java
    @Scheduled(fixedRate = 60000)
    public void populateStockDatabase() throws StocksResponseException { ... }
    ``` 

# Proxy Config 
If you are running application and need to get around a proxy, then you must configure this project to use your system proxy.
In the `ApiApplication.java` class, add the proper host and port in the `main` function.

```java
public static void main(String[] args) {
    Properties props = System.getProperties();
    props.put("https.proxyHost", "your-https-proxy-host");
    props.put("https.proxyPort", "your-https-proxy-port");
    SpringApplication.run(ApiApplication.class, args);
}
```

# Run API via IDE

- [Setup local JDK under Project Settings and Structure in IntelliJ](https://www.jetbrains.com/help/idea/project-settings-and-structure.html)
- [Create Springboot Run Debug Configuration in IntelliJ](https://www.jetbrains.com/help/idea/run-debug-configuration.html) 
    * Under the configuration, there is an attribute called `Active profiles`, set this to `local` to utilize the 
    `application-local.yaml` on startup.
- After you have your environment setup, run the application via the play button in the IDE: [How to run the Springboot App in IntelliJ](https://www.jetbrains.com/help/idea/running-applications.html)

# Run API via Command Line

- If you don't want to run the application from the IDE, then you can run it from a unix emulator (i.e. Git Bash, Cmder) or Terminal (MacOS).
- Run the following bash commands in the root directory of this project.
- Note: The maven binaries have been included so that you may run the `mvnw` commands even if you don't have maven installed on your `$PATH`

### Windows
```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=local
```

### MacOS
```bash
./mvnw clean install
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=local
```

# Swagger

- Swagger includes automated documentation that allows you to TRY OUT the API endpoints on the browser!
- OpenAPI spec 3.0 is configured under `SwaggerConfig.java`
- Swagger UI: http://localhost:8080/swagger-ui/index.html
![swagger](./assets/swagger.png)
- OpenAPI spec 3.0.3: http://localhost:8080/v3/api-docs
- Stock data: http://localhost:8080/api/v1/stocks

# Spring Actuator Endpoints 

- The actuator endpoints are configured under the `application.yaml` file. 
- Health: http://localhost:8080/health
- Metrics: http://localhost:8080/metrics
- Mappings: http://localhost:8080/mappings
