package question_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import question_service.model.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
