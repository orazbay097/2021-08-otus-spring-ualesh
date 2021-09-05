package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.presenter.SurveyPresenter;

@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        SurveyPresenter surveyService = context.getBean(SurveyPresenter.class);
        surveyService.start();
    }
}
