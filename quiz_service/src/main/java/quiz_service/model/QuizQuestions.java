package quiz_service.model;

import jakarta.persistence.*;

@Entity
@Table(name="quiz_questions")
@IdClass(QuizQuestionsId.class)
public class QuizQuestions {

    @Id
    @Column(nullable = false)
    private Integer quizId;

    @Id
    @Column(nullable = false)
    private Long questionId;

    public QuizQuestions() {
    }

    public QuizQuestions(Integer quizId, Long questionId) {
        this.quizId = quizId;
        this.questionId = questionId;
    }

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

    @Override
    public String toString() {
        return "QuizQuestions{" +
                "questionId=" + questionId +
                '}';
    }
}
