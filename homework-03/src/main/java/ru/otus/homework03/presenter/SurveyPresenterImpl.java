package ru.otus.homework03.presenter;

import org.springframework.stereotype.Component;
import ru.otus.homework03.domain.Person;
import ru.otus.homework03.domain.Survey;
import ru.otus.homework03.helpers.LocaleHelper;
import ru.otus.homework03.service.SurveyService;

import java.util.Scanner;

@Component
public class SurveyPresenterImpl implements SurveyPresenter {
    private final SurveyService surveyService;
    private final LocaleHelper localeHelper;


    public SurveyPresenterImpl(SurveyService surveyService, LocaleHelper localeHelper) {
        this.surveyService = surveyService;
        this.localeHelper = localeHelper;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);

        System.out.println(this.localeHelper.translate("survey.welcome-text"));
        System.out.println(this.localeHelper.translate("survey.enter-surname"));
        String surname = scan.nextLine();
        System.out.println(this.localeHelper.translate("survey.enter-name"));
        String name = scan.nextLine();

        Survey survey = surveyService.createSurvey(new Person(surname, name));


        while (!survey.isFinished()) {
            System.out.println(survey.getCurrentQuestion().getQuestionText());
            surveyService.answerToCurrentSurveyQuestion(survey, scan.nextLine());
        }

        System.out.println(this.localeHelper.translate("survey.result-text",
                new Object[]{
                        survey.getAuthor().getSurname(),
                        survey.getAuthor().getName(),
                        survey.getScore(),
                        this.localeHelper.translate(String.format("survey.pass.%s",survey.isPassed()))
                }));
    }
}
