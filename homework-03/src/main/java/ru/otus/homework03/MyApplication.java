package ru.otus.homework03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework03.presenter.SurveyPresenter;

@SpringBootApplication
public class MyApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MyApplication.class, args);
		SurveyPresenter surveyService = context.getBean(SurveyPresenter.class);
		surveyService.start();
	}

}
