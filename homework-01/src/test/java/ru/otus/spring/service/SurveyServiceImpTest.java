package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class SurveyServiceImpTest {
    @Mock
    private QuestionDao questionDao;
    @InjectMocks
    private SurveyServiceImp surveyServiceImp;

    @BeforeEach
    void mockQuestions() {
        given(this.questionDao.getAll())
                .willReturn(new ArrayList<>(
                                Arrays.asList(
                                        new Question("A", "1"),
                                        new Question("B", "2"),
                                        new Question("C", "3"),
                                        new Question("D", "4"),
                                        new Question("E", "5")
                                )
                        )
                );
    }

    @DisplayName("возвращает первый вопрос на который еще не ответили")
    @Test
    void shouldReturnFirstQuestion(){
        surveyServiceImp.answerToCurrentQuestion("1");
        assertThat(this.surveyServiceImp.getCurrentQuestionText())
                .isEqualTo("B");
    }

    @DisplayName("добавляет очко если правильно ответили")
    @Test
    void shouldIncrementScoreWhenCorrectAnswer(){
        surveyServiceImp.answerToCurrentQuestion("1");
        assertThat(this.surveyServiceImp.getScore())
                .isEqualTo(1);
    }

    @DisplayName("не добавляет очко если неправильно ответили")
    @Test
    void shouldNotIncrementScoreWhenCorrectAnswer(){
        surveyServiceImp.answerToCurrentQuestion("2");
        assertThat(this.surveyServiceImp.getScore())
                .isEqualTo(0);
    }





}