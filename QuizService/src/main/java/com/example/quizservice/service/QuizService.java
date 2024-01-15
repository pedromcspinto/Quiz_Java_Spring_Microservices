package com.example.quizservice.service;


import com.example.quizservice.dao.QuizDao;
import com.example.quizservice.feign.QuizInterface;
import com.example.quizservice.model.QuestionWrapper;
import com.example.quizservice.model.Quiz;
import com.example.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;
//    @Autowired
//    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String difficulty, int numQ, String title) {
        try{
            List<Integer> questions = quizInterface.getQuestionsForQuiz(difficulty, numQ).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestionsIds(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionsIds = quiz.getQuestionsIds();
        return quizInterface.getQuestionsFromId(questionsIds);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
