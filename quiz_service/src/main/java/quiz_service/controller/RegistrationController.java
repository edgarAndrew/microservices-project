package quiz_service.controller;


import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz_service.DTO.*;
import quiz_service.model.Quiz;
import quiz_service.service.QuizService;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    private final QuizService quizService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    public RegistrationController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping
    @CircuitBreaker(name="result_service_breaker", fallbackMethod = "resultServiceFallback")
    public ResponseEntity<RegisterQuizResponse> registerForQuiz(@RequestBody RegisterQuizRequest registrationDTO) {
        quizService.registerToQuiz(registrationDTO.getQuizId(),registrationDTO.getUserId());
        return ResponseEntity.ok(new RegisterQuizResponse("User has been registered to quiz"));
    }

    public ResponseEntity<GenericResponse> resultServiceFallback(Exception e){
        String state = circuitBreakerRegistry.circuitBreaker("resultServiceFallback").getState().name();
        String failureRate = String.valueOf(circuitBreakerRegistry.circuitBreaker("resultServiceFallback").getMetrics().getFailureRate());
        String failureThreshold = String.valueOf(circuitBreakerRegistry.circuitBreaker("resultServiceFallback").getCircuitBreakerConfig().getFailureRateThreshold());

        // Include details in the fallback response
        return ResponseEntity.ok(new GenericResponse(
                "-> Fallback response due to: " + e.getMessage() + " -> Circuit Breaker State: " + state + "-> Failure Threshold: " + failureThreshold +
                        "-> Failure Rate: " + failureRate
        ));
    }
}
