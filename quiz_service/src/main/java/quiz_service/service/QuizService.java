package quiz_service.service;

import jakarta.transaction.Transactional;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import quiz_service.DTO.GenericResponse;
import quiz_service.DTO.GetQuizResponse;
import quiz_service.exceptions.QuizAlreadyRegisteredException;
import quiz_service.exceptions.ResourceNotFoundException;
import quiz_service.model.*;
import quiz_service.repository.QuizQuestionsRepository;
import quiz_service.repository.QuizRegistrationRepository;
import quiz_service.repository.QuizRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizQuestionsRepository quizQuestionsRepository;
    private final QuizRegistrationRepository quizRegistrationRepository;
    private  final QuestionClient questionClient;
    private final ResultClient resultClient;


    public QuizService(QuizRepository quizRepository,QuizQuestionsRepository quizQuestionsRepository,QuizRegistrationRepository quizRegistrationRepository,QuestionClient questionClient,ResultClient resultClient){
        this.quizRepository = quizRepository;
        this.quizQuestionsRepository = quizQuestionsRepository;
        this.quizRegistrationRepository = quizRegistrationRepository;
        this.questionClient = questionClient;
        this.resultClient = resultClient;
    }

    //Method to add questions to a quiz
    public void addQuizQuestions(Integer quizId,Long questionId){

        quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id " + quizId));

        QuizQuestions quizQuestion = new QuizQuestions(quizId,questionId);
        quizQuestionsRepository.save(quizQuestion);
    }

    // Method to add a new quiz
    public Quiz addQuizDetails(String title, String description, String quizDate) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setUpdatedAt(LocalDateTime.now());
        quiz.setQuizDate(quizDate);
        return quizRepository.save(quiz);
    }

    // Method to delete a quiz by id
    @Transactional
    public void deleteQuiz(Integer id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            quizRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Quiz not found with id " + id);
        }
    }

    // Method to update an existing quiz
    @Transactional
    public Quiz updateQuiz(Integer id, String title, String description,String quizDate) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            quiz.setTitle(title);
            quiz.setDescription(description);
            quiz.setQuizDate(quizDate);
            quiz.setUpdatedAt(LocalDateTime.now());
            return quizRepository.save(quiz);
        } else {
            throw new ResourceNotFoundException("Quiz not found with id " + id);
        }
    }

    // Method to view a quiz by id
    public GetQuizResponse viewQuiz(Integer id) {
        Quiz quiz =  quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id " + id));

        List<Question> temp =  quizQuestionsRepository.findByQuizId(id).stream().map(item ->
            questionClient.getQuestion(item.getQuestionId())
        ).toList();

        return new GetQuizResponse(quiz,temp);
    }

    // Method to view all quizzes
    public List<Quiz> viewAllQuizzes() {
        return quizRepository.findAll();
    }

    //Method to register user to a quiz
    public void registerToQuiz(Integer quizId,Long userId){

        Quiz temp = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id " + quizId));

        QuizRegistrationId quizRegistrationId = new QuizRegistrationId(quizId, userId);

        // Check if the user is already registered for the quiz
        boolean exists = quizRegistrationRepository.existsById(quizRegistrationId);
        if (exists) {
            throw new QuizAlreadyRegisteredException("User is already registered for this quiz.");
        }

        // Check if the quiz date is in the past
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate quizDate = LocalDate.parse(temp.getQuizDate(), formatter);

        if (quizDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot register to a quiz that is already in the past.");
        }

        // calling another microservice
        ResponseEntity<GenericResponse> responseEntity = resultClient.registerUserForQuiz(userId, quizId);

        if (responseEntity.getStatusCode().value() != 200) {
            throw new ResourceNotFoundException("A problem has occurred with the results microservice");
        }

        // Output the status code and response


        QuizRegistration quizRegistration = new QuizRegistration(quizId,userId,LocalDate.now().format(formatter));
        quizRegistrationRepository.save(quizRegistration);

    }

}

