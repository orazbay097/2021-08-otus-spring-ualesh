package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Survey;

public class SurveyServiceImpl implements SurveyService {
    private final QuestionDao questionDao;

    public SurveyServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Survey createSurvey() {
        return new Survey(this.questionDao.getAll());
    }
}
