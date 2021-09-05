package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.Queue;

public interface QuestionDao {
    Queue<Question> getAll();
}
