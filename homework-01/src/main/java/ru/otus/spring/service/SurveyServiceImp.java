package ru.otus.spring.service;

import ru.otus.spring.dao.NoMoreQuestionsException;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;

public class SurveyServiceImp implements SurveyService{
    private final QuestionDao questionDao;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public SurveyServiceImp(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String getCurrentQuestionText() {
        if(this.isFinished()){
            throw new NoMoreQuestionsException();
        }
        return this.getQuestions().get(this.currentQuestionIndex).getQuestionText();
    }

    @Override
    public void answerToCurrentQuestion(String answer) {
        if(this.isFinished()){
            throw new NoMoreQuestionsException();
        }

        if(this.getQuestions().get(this.currentQuestionIndex).getAnswerText().equals(answer.toLowerCase())){
            this.score++;
        }
        this.currentQuestionIndex++;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public boolean isFinished() {
        return this.currentQuestionIndex>=this.getQuestions().size();
    }

    private ArrayList<Question> getQuestions(){
        return  this.questionDao.getAll();
    }
}
