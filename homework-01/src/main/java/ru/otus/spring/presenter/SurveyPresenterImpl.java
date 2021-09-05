package ru.otus.spring.presenter;

import ru.otus.spring.domain.Survey;
import ru.otus.spring.service.SurveyService;

import java.util.Scanner;

public class SurveyPresenterImpl implements  SurveyPresenter{
    private final SurveyService surveyService;

    public SurveyPresenterImpl(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public void start() {
        Survey survey = surveyService.createSurvey();
        Scanner scan = new Scanner(System.in);

        while (!survey.isFinished()){
            System.out.println(survey.getCurrentQuestionText());
            survey.answerToCurrentQuestion(scan.nextLine());
        }

        System.out.printf("Your score is: %d%n", survey.getScore());
    }
}
