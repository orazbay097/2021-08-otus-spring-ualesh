package ru.otus.homework04.dao;

import ru.otus.homework04.domain.Question;

import java.util.Queue;

public interface QuestionDao {
    Queue<Question> getAll();
}
