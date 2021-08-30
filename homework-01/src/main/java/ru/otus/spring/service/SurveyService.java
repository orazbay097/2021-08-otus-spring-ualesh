package ru.otus.spring.service;

import ru.otus.spring.dao.NoMoreQuestionsException;

public interface SurveyService {

    String getCurrentQuestionText() throws NoMoreQuestionsException;
    void answerToCurrentQuestion(String answer) throws NoMoreQuestionsException;
    int getScore();
    boolean isFinished();
}
