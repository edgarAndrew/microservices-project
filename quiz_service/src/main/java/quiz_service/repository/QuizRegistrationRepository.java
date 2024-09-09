package quiz_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quiz_service.model.QuizRegistration;
import quiz_service.model.QuizRegistrationId;

@Repository
public interface QuizRegistrationRepository extends JpaRepository<QuizRegistration, QuizRegistrationId> {

}
