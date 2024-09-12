package quiz_service.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quiz_service.DTO.*;
import quiz_service.model.Quiz;
import quiz_service.model.QuizQuestions;
import quiz_service.service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<AddQuizResponse> addQuiz(@RequestBody AddQuizRequest quizDTO) {
        Quiz newQuiz = quizService.addQuizDetails(quizDTO.getTitle(), quizDTO.getDescription(),quizDTO.getQuizDate());
        return ResponseEntity.ok(new AddQuizResponse("Quiz created"));
    }

    @PutMapping
    public ResponseEntity<AddQuizResponse> updateQuiz(@RequestParam Integer id, @RequestBody AddQuizRequest quizDTO) {
        Quiz updatedQuiz = quizService.updateQuiz(id, quizDTO.getTitle(), quizDTO.getDescription(),quizDTO.getQuizDate());
        return ResponseEntity.ok(new AddQuizResponse("Quiz updated"));
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.viewAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    // resilience4j configurations are fetched from the config server

    @GetMapping("/{id}")
    @CircuitBreaker(name="question_service_breaker", fallbackMethod = "questionServiceFallback")
    public ResponseEntity<GetQuizResponse> getQuiz(@PathVariable Integer id) {
        return ResponseEntity.ok(quizService.viewQuiz(id));
    }

    public ResponseEntity<GenericResponse> questionServiceFallback(Exception e){

        String state = circuitBreakerRegistry.circuitBreaker("question_service_breaker").getState().name();
        String failureRate = String.valueOf(circuitBreakerRegistry.circuitBreaker("question_service_breaker").getMetrics().getFailureRate());
        String failureThreshold = String.valueOf(circuitBreakerRegistry.circuitBreaker("question_service_breaker").getCircuitBreakerConfig().getFailureRateThreshold());

        // Include details in the fallback response
        return ResponseEntity.ok(new GenericResponse(
                "-> Fallback response due to: " + e.getMessage() + " -> Circuit Breaker State: " + state + "-> Failure Threshold: " + failureThreshold +
                        "-> Failure Rate: " + failureRate
        ));
    }

    @DeleteMapping
    public ResponseEntity<AddQuizResponse> removeQuiz(@RequestParam Integer id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok(new AddQuizResponse("Quiz deleted"));
    }


    @PostMapping("/add-questions")
    public ResponseEntity<AddQuizResponse> createQuizQuestions(@RequestBody AddQuizQuestionsRequest questionIdDTO) {
        List<Long> questionIds = questionIdDTO.getQuestionId();
        Integer quizId = questionIdDTO.getQuizId();

        for (Long questionId : questionIds) {
            quizService.addQuizQuestions(quizId, questionId);
        }

        return ResponseEntity.ok(new AddQuizResponse("Questions added to quiz"));
    }
}

