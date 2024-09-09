package quiz_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quiz_service.DTO.AddQuizQuestionsRequest;
import quiz_service.DTO.AddQuizRequest;
import quiz_service.DTO.AddQuizResponse;
import quiz_service.DTO.GetQuizResponse;
import quiz_service.model.Quiz;
import quiz_service.model.QuizQuestions;
import quiz_service.service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

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

    @GetMapping("/{id}")
    public ResponseEntity<GetQuizResponse> getQuiz(@PathVariable Integer id) {
        return ResponseEntity.ok(quizService.viewQuiz(id));
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

