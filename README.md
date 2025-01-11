
# Quiz Management System

### Project Architecture
[![architecture.png](https://i.postimg.cc/59qcvbHz/architecture.png)](https://postimg.cc/DmZY3VPf)

### ER Diagram
[![er-diagram.png](https://i.postimg.cc/ncmXLBXp/er-diagram.png)](https://postimg.cc/9rWm84Pn)

### Overview
This project is a **microservices-based quiz management system** built using Java and the Spring ecosystem and React for the frontend. It follows a modular architecture with each service handling specific responsibilities, ensuring scalability and maintainability. Key components of the system include:

## Features

- **API Gateway**: Manages client requests with integrated rate limiting and load balancing.
- **Distributed Tracing**: Implements monitoring and observability across services.
- **Service Registry**: Enables dynamic service discovery.
- **Config Server**: Centralized configuration management connected to a GitHub repository.
- **Client Application**: A user-friendly frontend built with React for seamless interaction.

## Microservices

1. **User Service**: Handles user-related operations with a dedicated MySQL database.
2. **Quiz Service**: Manages quiz creation and participation, including circuit breaker integration for fault tolerance.
3. **Question Service**: Responsible for managing quiz questions and their content.
4. **Results Service**: Handles storage and retrieval of quiz results.

## Technology Stack

- **Frontend**: React
- **Backend**: Java with Spring Boot and Spring Cloud
- **Databases**: MySQL
- **Configuration Management**: GitHub (via Config Server)

This system leverages the Spring ecosystem and React to provide a robust, scalable, and user-friendly platform for managing quizzes effectively.

### Pre-requisite
- Download maven from [here](https://maven.apache.org/download.cgi?.)
- Download config yml file from [here](https://www.mediafire.com/file/pxm071if69om5p5/application.yml/file) and place it in 'ConfigServer\src\main\resources'

### Order of execution
- Distributed Tracing
- Service Discovery
- Config Server
- Users Service
- Quiz Service
- Question Service
- Results Service
- API Gateway

### To start zipkin server for distributed tracing
```
cd distributedTracing
java -jar .\zipkin-server-3.4.1-exec.jar --server.port=8088
```

### To build a service
cd into the service
```
mvn clean install -DskipTests
```

### To run a service
cd into the service
```
mvn spring-boot:run
```

NOTE: Rate limiter is under development

Import the 'quiz microservices project.postman_collection' file provided inside postman to view API endpoints
