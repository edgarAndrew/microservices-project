
# Getting Started

### Project Architecture
[![architecture.png](https://i.postimg.cc/59qcvbHz/architecture.png)](https://postimg.cc/DmZY3VPf)

### ER Diagram
[![er-diagram.png](https://i.postimg.cc/ncmXLBXp/er-diagram.png)](https://postimg.cc/9rWm84Pn)

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
