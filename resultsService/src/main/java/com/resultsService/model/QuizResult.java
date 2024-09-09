package com.resultsService.model;

import jakarta.persistence.*;

@Entity
@Table(name="quiz_results")
@IdClass(QuizResultId.class)
public class QuizResult {

    @Id
    @Column(nullable = false)
    private Integer quizId;

    @Id
    @Column(nullable = false)
    private Long userId;

    @Column
    private Integer score;

    public QuizResult() {
    }

    public QuizResult(Integer quizId, Long userId,Integer score) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "quizId=" + quizId +
                ", userId=" + userId +
                ", score=" + score +
                '}';
    }
}

