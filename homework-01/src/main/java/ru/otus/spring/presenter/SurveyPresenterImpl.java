package ru.otus.spring.presenter;

import ru.otus.spring.service.SurveyService;

import java.util.Scanner;

public class SurveyPresenterImpl implements  SurveyPresenter{
    private final SurveyService surveyService;

    public SurveyPresenterImpl(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);

        while (!this.surveyService.isFinished()){
            System.out.println(this.surveyService.getCurrentQuestionText());
            this.surveyService.answerToCurrentQuestion(scan.nextLine());
        }

        System.out.printf("Your score is: %d%n", this.surveyService.getScore());
    }
}
