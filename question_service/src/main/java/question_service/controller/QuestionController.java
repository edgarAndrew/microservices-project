package question_service.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import question_service.DTO.UpdateQuestionRequest;
import question_service.DTO.AddQuestionResponce;
import question_service.model.Question;
import question_service.service.QuestionService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
    private final QuestionService questionService;
    @Autowired
    private EurekaClient eurekaClient;
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id){
        return ResponseEntity.ok(questionService.getQuestion(id));
    }

    @PostMapping
    public ResponseEntity<AddQuestionResponce> addQuestion(@RequestBody List<Question> questionDTO) {
        //questionService.addQuestion(questionDTO.getDescription(), questionDTO.getOptions(),questionDTO.getAnswer());
        questionService.addQuestions(questionDTO);
        return ResponseEntity.ok(new AddQuestionResponce("Question added"));
    }

    @PutMapping
    public ResponseEntity<AddQuestionResponce> updateQuestion(@RequestParam Long id, @RequestBody UpdateQuestionRequest questionDTO) {
        questionService.updateQuestion(id, questionDTO.getDescription(), questionDTO.getOptions(),questionDTO.getAnswer());
        return ResponseEntity.ok(new AddQuestionResponce("Question updated"));
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestionzes() {
        List<Question> Questions = questionService.getAllQuestions();
        return ResponseEntity.ok(Questions);
    }

    @DeleteMapping
    public ResponseEntity<AddQuestionResponce> removeQuestion(@RequestParam Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(new AddQuestionResponce("Question deleted"));
    }

}
