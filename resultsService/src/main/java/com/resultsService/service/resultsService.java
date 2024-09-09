package com.resultsService.service;

import com.resultsService.exceptions.ResourceNotFoundException;
import com.resultsService.model.QuizResult;
import com.resultsService.repository.QuizResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class resultsService {

    private final QuizResultRepository quizResultRepository;

    public resultsService(QuizResultRepository quizResultRepository){
        this.quizResultRepository = quizResultRepository;
    }

    public void initializeRegistration(Integer quizId,Long userId) {
        quizResultRepository.save(new QuizResult(quizId, userId, -1));
    }

    public void submitScore(Integer quizId,Long userId,Integer score){
        Optional<QuizResult> existingResult = quizResultRepository.findByQuizIdAndUserId(quizId, userId);

        if (existingResult.isPresent()) {
            // If the result exists, update the score
            QuizResult quizResult = existingResult.get();
            if (quizResult.getScore() != -1){
                throw new IllegalArgumentException("Score for this quiz is already submitted");
            }else{
                quizResult.setScore(score);
                quizResultRepository.save(quizResult);
            }

        } else {
            throw new ResourceNotFoundException("You have not registered for this quiz");
        }
    }

    public List<QuizResult> getResult(Long userId) {
        return quizResultRepository.findAllByUserId(userId);
    }

    public Integer getResult(Long userId,Integer quizId) {
        Optional<QuizResult> temp = quizResultRepository.findByQuizIdAndUserId(quizId, userId);
        return temp.map(QuizResult::getScore)
                .orElseThrow(() -> new ResourceNotFoundException("No result found for quiz with quizId: " + quizId + " and userId: " + userId));
    }

}
