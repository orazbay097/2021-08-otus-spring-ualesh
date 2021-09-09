package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

@Service
public class QuestionCheckerServiceImpl implements QuestionCheckerService {

    @Override
    public boolean check(Question question, String answer) {
        return question.getAnswerText().equals(answer);
    }
}
