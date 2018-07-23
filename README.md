# Software Required
- [Java10 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [MongoDB](https://www.mongodb.com/download-center#community)

# Run API Service Locally 

Run the following in the root directory: 

## Windows
```
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

## MacOS
```
./mvnw clean install
./mvnw spring-boot:run
```

## Links

### Swagger
- UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

![swagger](./assets/swagger.png)
- API Spec: [http://localhost:8080/api/v1/stocks](http://localhost:8080/api/v1/stocks)
    - extracted JSON under `/assets` directory
    
### Spring Actuators
- Health: [http://localhost:8080/health](http://localhost:8080/health)
- Metrics: [http://localhost:8080/metrics](http://localhost:8080/metrics)
- HTTP Trace: [http://localhost:8080/httptrace](http://localhost:8080/httptrace)
- Mappings: [http://localhost:8080/mappings](http://localhost:8080/mappings)