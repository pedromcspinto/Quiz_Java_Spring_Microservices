package com.example.questionservice.service;


import com.example.questionservice.dao.QuestionDao;
import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByDifficultylevel(String difficulty) {
        try {
            return new ResponseEntity<>(questionDao.findByDifficultylevelIgnoreCase(difficulty)
                    ,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String difficultyLevel, Integer numQuestions) {
            List<Integer> questions = questionDao.findRandomQuestionsByDifficultylevelIgnoreCase(difficultyLevel, numQuestions);
            return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for(Integer id : questionIds){
            //questions.add(questionDao.findById(id).get());
            Question question = questionDao.findById(id).get();
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }

//        for(Question question : questions){
//            QuestionWrapper wrapper = new QuestionWrapper();
//            wrapper.setId(question.getId());
//            wrapper.setQuestionTitle(question.getQuestionTitle());
//            wrapper.setOption1(question.getOption1());
//            wrapper.setOption2(question.getOption2());
//            wrapper.setOption3(question.getOption3());
//            wrapper.setOption4(question.getOption4());
//            wrappers.add(wrapper);
//        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response response : responses){
            Question questions = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(questions.getRightAnswer()))
                right++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
