package ru.otus.homework03.service;


import ru.otus.homework03.domain.Person;
import ru.otus.homework03.domain.Survey;

public interface SurveyService {
    Survey createSurvey(Person author);
    void answerToCurrentSurveyQuestion(Survey survey, String answer);
}
