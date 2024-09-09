package quiz_service.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz_service.DTO.AddQuizRequest;
import quiz_service.DTO.AddQuizResponse;
import quiz_service.DTO.RegisterQuizRequest;
import quiz_service.DTO.RegisterQuizResponse;
import quiz_service.model.Quiz;
import quiz_service.service.QuizService;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    private final QuizService quizService;

    public RegistrationController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<RegisterQuizResponse> registerForQuiz(@RequestBody RegisterQuizRequest registrationDTO) {
        quizService.registerToQuiz(registrationDTO.getQuizId(),registrationDTO.getUserId());
        return ResponseEntity.ok(new RegisterQuizResponse("User has been registered to quiz"));
    }
}
