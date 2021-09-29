package ru.otus.homework04.helpers;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.homework04.config.SurveyConfig;

import java.util.Locale;

@Component
public class LocaleHelper {
    private final MessageSource messageSource;
    private final String languageTag;

    public LocaleHelper(MessageSource messageSource, SurveyConfig surveyConfig) {
        this.messageSource = messageSource;
        this.languageTag = surveyConfig.getLanguageTag();
    }

    public String getMessage(String key, Object... arguments) {
        return this.messageSource.getMessage(key, arguments, Locale.forLanguageTag(this.languageTag));
    }

    public String getLanguageTag() {
        return this.languageTag;
    }
}