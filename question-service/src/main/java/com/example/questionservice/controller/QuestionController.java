package com.example.questionservice.controller;


import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;
import com.example.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("difficulty/{difficulty}")
    public ResponseEntity<List<Question>> getQuestionByDifficulty(@PathVariable String difficulty){
        return questionService.getQuestionsByDifficultylevel(difficulty);
    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>>getQuestionsForQuiz(
            @RequestParam String difficultyLevel, @RequestParam int numQuestions){
        return questionService.getQuestionsForQuiz(difficultyLevel,numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }


    // generate
    // getQuestions (questionid)
    // getScore
}