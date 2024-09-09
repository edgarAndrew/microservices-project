package quiz_service.model;

import java.io.Serializable;
import java.util.Objects;

public class QuizRegistrationId implements Serializable {
    private Integer quizId;
    private Long userId;

    // Default constructor
    public QuizRegistrationId() {
    }

    // Parameterized constructor
    public QuizRegistrationId(Integer quizId, Long userId) {
        this.quizId = quizId;
        this.userId = userId;
    }

    // Getters and setters
    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizRegistrationId that = (QuizRegistrationId) o;
        return Objects.equals(quizId, that.quizId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, userId);
    }
}

