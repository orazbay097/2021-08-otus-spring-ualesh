package ru.otus.homework04.service;

import org.springframework.stereotype.Service;
import ru.otus.homework04.domain.Question;

@Service
public class QuestionCheckerServiceImpl implements QuestionCheckerService {

    @Override
    public boolean check(Question question, String answer) {
        return question.getAnswerText().equalsIgnoreCase(answer);
    }
}
