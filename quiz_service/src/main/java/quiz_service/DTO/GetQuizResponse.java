package quiz_service.DTO;


import quiz_service.model.Question;
import quiz_service.model.Quiz;
import quiz_service.model.QuizQuestions;

import java.util.List;

public class GetQuizResponse {
    private Quiz quiz;
    private List<Question> questions;

    public GetQuizResponse() {
    }

    public GetQuizResponse(Quiz quiz, List<Question> questions) {
        this.quiz = quiz;
        this.questions = questions;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "GetQuizResponse{" +
                "quiz=" + quiz +
                ", questions=" + questions +
                '}';
    }
}
