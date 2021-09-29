package ru.otus.homework04.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework04.config.SurveyConfig;
import ru.otus.homework04.dao.QuestionDao;
import ru.otus.homework04.domain.Person;
import ru.otus.homework04.domain.Question;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SurveyServiceImplTest {
    @Mock
    private QuestionDao questionDao;
    @Mock
    private QuestionCheckerService questionCheckerService;


    @DisplayName("создает опрос")
    @Test
    void shouldCreateSurvey(){
        SurveyConfig surveyConfig = new SurveyConfig();
        SurveyServiceImpl surveyServiceImpl = new SurveyServiceImpl(questionDao, questionCheckerService, surveyConfig);
        given(this.questionDao.getAll())
                .willReturn(new LinkedList<>(
                                Arrays.asList(
                                        new Question("A", "1"),
                                        new Question("B", "2"),
                                        new Question("C", "3"),
                                        new Question("D", "4"),
                                        new Question("E", "5")
                                )
                        )
                );

        assertThat(surveyServiceImpl.createSurvey(new Person("a","b")).getCurrentQuestion().getQuestionText())
                .isEqualTo("A");
    }
}