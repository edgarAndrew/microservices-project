package quiz_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import quiz_service.model.Question;

// FeignClient is provided by spring cloud to communicate between microservices

// 'name' is the service id of the microservice
// spring cloud load balancer will automatically handle which url (multiple instances) to call from the eureka server

@FeignClient(name="question-service")
//@FeignClient(url="localhost:8081")
public interface QuestionClient {

    @GetMapping("/api/v1/question/{id}")
    Question getQuestion(@PathVariable Long id);

}
