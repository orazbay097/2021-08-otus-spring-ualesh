package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.SurveyService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        SurveyService surveyService = context.getBean(SurveyService.class);

        Scanner scan = new Scanner(System.in);

        while (!surveyService.isFinished()){
            System.out.println(surveyService.getCurrentQuestionText());
            surveyService.answerToCurrentQuestion(scan.nextLine());
        }

        System.out.printf("Your score is: %d%n", surveyService.getScore());
    }
}
