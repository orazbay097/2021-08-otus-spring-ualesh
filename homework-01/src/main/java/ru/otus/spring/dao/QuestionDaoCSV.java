package ru.otus.spring.dao;

import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestionDaoCSV implements QuestionDao {
    private static final String COMMA_DELIMITER = ",";

    @Override
    public ArrayList<Question> getAll() {
        ArrayList<Question> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("questions.csv").getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                questions.add(new Question(values[0],values[1]));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return questions;
    }
}
