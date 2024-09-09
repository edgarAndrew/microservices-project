package quiz_service.DTO;

public class RegisterQuizRequest {
    private Integer quizId;
    private Long userId;

    public RegisterQuizRequest() {
    }

    public RegisterQuizRequest(Integer quizId, Long userId) {
        this.quizId = quizId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "RegisterQuizRequest{" +
                "quizId=" + quizId +
                ", userId=" + userId +
                '}';
    }
}
