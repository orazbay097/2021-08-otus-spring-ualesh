package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Survey;

@Service
public class SurveyServiceImpl implements SurveyService {
    private final QuestionDao questionDao;
    private final QuestionCheckerService questionCheckerService;
    private final int minPassScore;

    public SurveyServiceImpl(QuestionDao questionDao, QuestionCheckerService questionCheckerService, @Value("${pass.score}") int minPassScore) {
        this.questionDao = questionDao;
        this.questionCheckerService = questionCheckerService;
        this.minPassScore = minPassScore;
    }

    @Override
    public Survey createSurvey(Person author) {
        return new Survey(author, this.questionDao.getAll(), this.minPassScore);
    }

    @Override
    public void answerToCurrentSurveyQuestion(Survey survey, String answer) {
        survey.answerToCurrentQuestion(this.questionCheckerService.check(survey.getCurrentQuestion(),answer));
    }
}
