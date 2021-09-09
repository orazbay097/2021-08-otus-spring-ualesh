package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public interface QuestionCheckerService {
    boolean check(Question question, String answer);
}
