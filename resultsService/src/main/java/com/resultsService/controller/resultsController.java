package com.resultsService.controller;

import com.resultsService.DTO.GenericResponse;
import com.resultsService.DTO.GetResultResponse;
import com.resultsService.model.QuizResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.resultsService.service.resultsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/results")
public class resultsController {
    private final resultsService resultsService;

    public resultsController(resultsService resultsService){
        this.resultsService = resultsService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> initializeRegistration(@RequestParam Long userId, @RequestParam Integer quizId) {
        if(userId != null && quizId != null){
            resultsService.initializeRegistration(quizId,userId);
            return ResponseEntity.ok(new GenericResponse("successfully initialized in results service"));
        }else{
            throw new IllegalArgumentException("userId and quizId must be provided");
        }

    }

    @PutMapping
    public ResponseEntity<GenericResponse> submitScore(@RequestParam Long userId, @RequestParam Integer quizId,@RequestParam Integer score) {
        if(userId != null && quizId != null && score != null){
            resultsService.submitScore(quizId,userId,score);
            return ResponseEntity.ok(new GenericResponse("Score has been submitted successfully"));
        }else{
            throw new IllegalArgumentException("userId,quizId,score must be provided");
        }

    }

    @GetMapping
    public ResponseEntity<GetResultResponse> getResult(@RequestParam(required = true) Long userId, @RequestParam(required = false) Integer quizId) {
        GetResultResponse res = new GetResultResponse();

        if(quizId == null){
            res.setScores(resultsService.getResult(userId));
            return ResponseEntity.ok(res) ;
        }
        else{
            res.setScore(resultsService.getResult(userId,quizId));
            return ResponseEntity.ok(res);
        }
    }
}
