package ru.otus.homework04.service;


import ru.otus.homework04.domain.Person;
import ru.otus.homework04.domain.Survey;

public interface SurveyService {
    Survey createSurvey(Person author);
    void answerToCurrentSurveyQuestion(Survey survey, String answer);
}
