package ru.otus.homework04.service;


import ru.otus.homework04.domain.Question;

public interface QuestionCheckerService {
    boolean check(Question question, String answer);
}
