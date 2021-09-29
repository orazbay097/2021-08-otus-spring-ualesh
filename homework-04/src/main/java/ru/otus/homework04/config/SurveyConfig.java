package ru.otus.homework04.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "survey")
@Component
public class SurveyConfig {
    private String baseCsvFilename;
    private int passScore;
    private String languageTag;

    public String getBaseCsvFilename() {
        return baseCsvFilename;
    }

    public void setBaseCsvFilename(String baseCsvFilename) {
        this.baseCsvFilename = baseCsvFilename;
    }

    public int getPassScore() {
        return passScore;
    }

    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }
}
