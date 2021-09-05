package ru.otus.spring.service;

import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Survey;

public interface SurveyService {
    Survey createSurvey(Person author);
}
