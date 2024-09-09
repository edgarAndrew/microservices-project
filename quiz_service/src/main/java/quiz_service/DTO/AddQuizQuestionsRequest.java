package quiz_service.DTO;

import java.util.List;

public class AddQuizQuestionsRequest {
    private List<Long> questionId;
    private Integer quizId;

    // Default constructor
    public AddQuizQuestionsRequest() {
    }

    // Parameterized constructor
    public AddQuizQuestionsRequest(Integer quizId,List<Long> questionId) {
        this.questionId = questionId;
        this.quizId = quizId;
    }

    // Getter and setter
    public List<Long> getQuestionId() {
        return questionId;
    }

    public void setQuestionId(List<Long> questionId) {
        this.questionId = questionId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }
}
