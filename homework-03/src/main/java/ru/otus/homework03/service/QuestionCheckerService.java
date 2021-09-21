package ru.otus.homework03.service;


import ru.otus.homework03.domain.Question;

public interface QuestionCheckerService {
    boolean check(Question question, String answer);
}
