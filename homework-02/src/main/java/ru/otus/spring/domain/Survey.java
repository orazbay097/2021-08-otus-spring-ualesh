package ru.otus.spring.domain;

import ru.otus.spring.exceptions.NoMoreQuestionsException;

import java.util.Queue;

public class Survey {
    private final Person author;
    private final Queue<Question> questions;
    private int score = 0;
    private final int minPassScore;

    public Survey(Person author, Queue<Question> questions, int minPassScore) {
        this.author = author;
        this.questions = questions;
        this.minPassScore = minPassScore;
    }

    public Question getCurrentQuestion() {
        try {
            return this.questions.peek();
        } catch (NullPointerException e) {
            throw new NoMoreQuestionsException();
        }
    }

    public void answerToCurrentQuestion(boolean isRightAnswer) {
        if (this.isFinished()) {
            throw new NoMoreQuestionsException();
        }
        if (isRightAnswer) {
            this.score++;
        }
        this.questions.remove();
    }


    public String getResult() {
        return String.format("Dear, %s %s, your score is %d.\nYou are %s.",
                this.author.getSurname(), this.author.getName(), this.score, this.isPassed() ? "passed" : "failed");
    }

    private boolean isPassed() {
        return this.score >= this.minPassScore;
    }

    public boolean isFinished() {
        return this.questions.isEmpty();
    }
}
