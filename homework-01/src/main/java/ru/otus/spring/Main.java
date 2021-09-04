package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.presenter.SurveyPresenter;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        SurveyPresenter surveyService = context.getBean(SurveyPresenter.class);
        surveyService.start();
    }
}
