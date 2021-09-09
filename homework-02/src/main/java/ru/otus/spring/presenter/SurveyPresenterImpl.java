package ru.otus.spring.presenter;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Survey;
import ru.otus.spring.service.SurveyService;

import java.util.Scanner;

@Component
public class SurveyPresenterImpl implements  SurveyPresenter{
    private final SurveyService surveyService;


    public SurveyPresenterImpl(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Hello, welcome to survey");
        System.out.println("Please, enter your surname:");
        String surname = scan.nextLine();
        System.out.println("... and name:");
        String name = scan.nextLine();

        Survey survey = surveyService.createSurvey(new Person(surname,name));


        while (!survey.isFinished()){
            System.out.println(survey.getCurrentQuestion().getQuestionText());
            surveyService.answerToCurrentSurveyQuestion(survey, scan.nextLine());
        }

        System.out.println(survey.getResult());
    }
}
