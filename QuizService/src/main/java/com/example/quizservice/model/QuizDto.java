package com.example.quizservice.model;

import lombok.Data;

@Data
public class QuizDto {
    String difficultyLevel;
    int numQuestions;
    String title;
}
