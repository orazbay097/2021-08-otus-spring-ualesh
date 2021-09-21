package ru.otus.homework03.dao;

import org.springframework.stereotype.Component;
import ru.otus.homework03.config.SurveyConfig;
import ru.otus.homework03.domain.Question;
import ru.otus.homework03.helpers.LocaleHelper;
import ru.otus.homework03.loader.Loader;
import ru.otus.homework03.loader.LoaderResource;
import ru.otus.homework03.parser.Parser;
import ru.otus.homework03.parser.QuestionParserCsv;

import java.util.Queue;

@Component
public class QuestionDaoCSV implements QuestionDao {


    private final Loader loader;
    private final Parser<Question> parser;
    private final String baseCsvFileName;
    private final LocaleHelper localeHelper;

    public QuestionDaoCSV(LoaderResource loader, QuestionParserCsv parser, SurveyConfig surveyConfig, LocaleHelper localeHelper) {
        this.loader = loader;
        this.parser = parser;
        this.baseCsvFileName = surveyConfig.getBaseCsvFilename();
        this.localeHelper = localeHelper;
    }


    @Override
    public Queue<Question> getAll() {
        return this.parser.parse(this.loader.load(this.getFullCsvFileName()));
    }

    private String getFullCsvFileName() {
        String languageTag = this.localeHelper.getLanguageTag();
        String localePostfix = languageTag.isEmpty() ? "" : String.format("_%s", languageTag);
        return String.format("%s%s.csv", this.baseCsvFileName, localePostfix);
    }
}
