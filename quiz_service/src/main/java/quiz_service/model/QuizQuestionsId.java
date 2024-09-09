package quiz_service.model;

import java.io.Serializable;
import java.util.Objects;

public class QuizQuestionsId implements Serializable {
    private Integer quizId;
    private Long questionId;

    // Default constructor
    public QuizQuestionsId() {
    }

    // Parameterized constructor
    public QuizQuestionsId(Integer quizId, Long questionId) {
        this.quizId = quizId;
        this.questionId = questionId;
    }

    // Getters and setters
    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizQuestionsId that = (QuizQuestionsId) o;
        return Objects.equals(quizId, that.quizId) && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, questionId);
    }
}

