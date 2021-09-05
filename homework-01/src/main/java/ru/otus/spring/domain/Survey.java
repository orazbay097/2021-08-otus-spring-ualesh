package ru.otus.spring.domain;

import ru.otus.spring.dao.NoMoreQuestionsException;

import java.util.Queue;

public class Survey {
    private Queue<Question> questions;
    private int score = 0;

    public Survey(Queue<Question> questions) {
        this.questions = questions;
    }

    public String getCurrentQuestionText() {
        try {
            return this.questions.peek().getQuestionText();
        }catch (NullPointerException e){
            throw new NoMoreQuestionsException();
        }
    }

    public void answerToCurrentQuestion(String answer) {
        if (this.isFinished()) {
            throw new NoMoreQuestionsException();
        }

        if (this.questions.peek().getAnswerText().equals(answer.toLowerCase())) {
            this.score++;
        }
        this.questions.remove();
    }

    public int getScore() {
        return this.score;
    }

    public boolean isFinished() {
        return this.questions.isEmpty();
    }
}
