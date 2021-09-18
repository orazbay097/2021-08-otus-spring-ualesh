package ru.otus.homework03.dao;

import ru.otus.homework03.domain.Question;

import java.util.Queue;

public interface QuestionDao {
    Queue<Question> getAll();
}
