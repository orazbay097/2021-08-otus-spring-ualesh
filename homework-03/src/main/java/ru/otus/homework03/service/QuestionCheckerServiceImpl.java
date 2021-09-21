package ru.otus.homework03.service;

import org.springframework.stereotype.Service;
import ru.otus.homework03.domain.Question;

@Service
public class QuestionCheckerServiceImpl implements QuestionCheckerService {

    @Override
    public boolean check(Question question, String answer) {
        return question.getAnswerText().equalsIgnoreCase(answer);
    }
}
