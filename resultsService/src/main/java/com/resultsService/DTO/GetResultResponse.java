package com.resultsService.DTO;

import com.resultsService.model.QuizResult;

import java.util.List;

public class GetResultResponse{
    List<QuizResult> scores;
    Integer score;

    public GetResultResponse(){
        this.scores = null;
        this.score = null;
    }


    public List<QuizResult> getScores() {
        return scores;
    }

    public void setScores(List<QuizResult> scores) {
        this.scores = scores;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
