package quiz_service.model;

import jakarta.persistence.*;

@Entity
@Table(name="quiz_registration")
@IdClass(QuizRegistrationId.class)
public class QuizRegistration {

    @Id
    @Column(nullable = false)
    private Integer quizId;

    @Id
    @Column(nullable = false)
    private Long userId;

    @Column
    private String regDate;

    public QuizRegistration() {
    }

    public QuizRegistration(Integer quizId, Long userId,String regDate) {
        this.quizId = quizId;
        this.userId = userId;
        this.regDate = regDate;
    }

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

    public String getregDate() {
        return regDate;
    }

    public void setregDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "QuizRegistration{" +
                "quizId=" + quizId +
                ", userId=" + userId +
                ", regDate=" + regDate +
                '}';
    }
}
