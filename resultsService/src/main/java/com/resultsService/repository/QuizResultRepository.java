package com.resultsService.repository;

import com.resultsService.model.QuizResult;
import com.resultsService.model.QuizResultId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, QuizResultId> {
    Optional<QuizResult> findByQuizIdAndUserId(Integer quizId, Long userId);
    List<QuizResult> findAllByUserId(Long userId);
}
