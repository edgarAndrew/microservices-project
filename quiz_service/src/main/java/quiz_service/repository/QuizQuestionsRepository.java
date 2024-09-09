package quiz_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quiz_service.model.QuizQuestions;
import quiz_service.model.QuizQuestionsId;

import java.util.List;

@Repository
public interface QuizQuestionsRepository extends JpaRepository<QuizQuestions, QuizQuestionsId> {
    List<QuizQuestions> findByQuizId(Integer quizId);
}
