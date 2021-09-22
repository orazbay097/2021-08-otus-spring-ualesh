package ru.otus.homework04.parser;

import org.springframework.stereotype.Component;
import ru.otus.homework04.domain.Question;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class QuestionParserCsv implements Parser<Question> {
    private static final String COMMA_DELIMITER = ",";

    @Override
    public Queue<Question> parse(String input) {
        Queue<Question> result = new LinkedList<>();
        input.lines().forEach((line -> {
            String[] splitedLine = line.split(COMMA_DELIMITER);
            result.add(new Question(splitedLine[0], splitedLine[1]));
        }));
        return result;
    }
}
