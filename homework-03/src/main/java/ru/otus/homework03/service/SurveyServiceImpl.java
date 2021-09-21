package ru.otus.homework03.service;

import org.springframework.stereotype.Service;
import ru.otus.homework03.config.SurveyConfig;
import ru.otus.homework03.dao.QuestionDao;
import ru.otus.homework03.domain.Person;
import ru.otus.homework03.domain.Survey;

@Service
public class SurveyServiceImpl implements SurveyService {
    private final QuestionDao questionDao;
    private final QuestionCheckerService questionCheckerService;
    private final int minPassScore;

    public SurveyServiceImpl(QuestionDao questionDao, QuestionCheckerService questionCheckerService, SurveyConfig surveyConfig) {
        this.questionDao = questionDao;
        this.questionCheckerService = questionCheckerService;
        this.minPassScore = surveyConfig.getPassScore();
    }

    @Override
    public Survey createSurvey(Person author) {
        return new Survey(author,
                this.questionDao.getAll(),
                this.minPassScore);
    }

    @Override
    public void answerToCurrentSurveyQuestion(Survey survey, String answer) {
        survey.answerToCurrentQuestion(this.questionCheckerService.check(survey.getCurrentQuestion(), answer));
    }
}
