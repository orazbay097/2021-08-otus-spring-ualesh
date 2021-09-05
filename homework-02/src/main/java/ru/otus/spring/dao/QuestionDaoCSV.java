package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Question;
import ru.otus.spring.loader.Loader;
import ru.otus.spring.loader.LoaderResource;
import ru.otus.spring.parser.Parser;
import ru.otus.spring.parser.QuestionParserCsv;

import java.util.Queue;

@Repository
public class QuestionDaoCSV implements QuestionDao {


    private final Loader loader;
    private final Parser<Question> parser;
    private final String csvFileName;

    public QuestionDaoCSV(LoaderResource loader, QuestionParserCsv parser,@Value("${csv.filename}") String csvFileName) {
        this.loader = loader;
        this.parser = parser;
        this.csvFileName = csvFileName;
    }


    @Override
    public Queue<Question> getAll() {
        return this.parser.parse(this.loader.load(this.csvFileName));
    }
}
