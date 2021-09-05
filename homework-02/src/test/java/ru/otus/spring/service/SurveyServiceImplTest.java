package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;

import java.util.Arrays;
import java.util.LinkedList;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SurveyServiceImpTest {
    @Mock
    private QuestionDao questionDao;
    @InjectMocks
    private SurveyServiceImpl surveyServiceImp;


    @DisplayName("создает опрос")
    @Test
    void shouldCreateSurvey(){
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

        assertThat(this.surveyServiceImp.createSurvey(new Person("a","b")).getCurrentQuestionText())
                .isEqualTo("A");
    }
}