package question_service.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import question_service.exceptions.ResourceNotFoundException;
import question_service.model.Question;
import question_service.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    public void addQuestion(String description, String options, String answer){
        Question question = new Question(description,options,answer);
        questionRepository.save(question);
    }
    public void addQuestions(List<Question> questions){
        questionRepository.saveAll(questions);
    }

    public void updateQuestion(Long id,String description, String options, String answer){
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if(optionalQuestion.isPresent()){
            Question question = optionalQuestion.get();
            question.setDescription(description);
            question.setOptions(options);
            question.setAnswer(answer);
            questionRepository.save(question);
        }else{
            throw new RuntimeException("Invalid Id");
        }
    }

    @Transactional
    public void deleteQuestion(Long id){
        Optional<?> question = questionRepository.findById(id);
        if(question.isPresent()){
            questionRepository.deleteById(id);
        }else{
            throw new RuntimeException("Invalid Id");
        }
    }

    public Question getQuestion(Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
    }

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

}
