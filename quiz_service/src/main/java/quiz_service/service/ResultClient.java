package quiz_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quiz_service.DTO.GenericResponse;
import quiz_service.configurations.FeignClientConfiguration;

@FeignClient(name="results-service",configuration = FeignClientConfiguration.class)
public interface ResultClient {

    @PostMapping("/api/v1/results")
    ResponseEntity<GenericResponse> registerUserForQuiz(@RequestParam("userId") Long userId, @RequestParam("quizId") Integer quizId);
}
