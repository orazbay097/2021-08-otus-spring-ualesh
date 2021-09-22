package ru.otus.homework04.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework04.domain.Person;
import ru.otus.homework04.domain.Survey;
import ru.otus.homework04.helpers.LocaleHelper;
import ru.otus.homework04.service.SurveyService;

import java.util.function.Supplier;

@ShellComponent
public class SurveyCommands {
    private final SurveyService surveyService;
    private final LocaleHelper localeHelper;

    private Person user;
    private Survey survey;

    public SurveyCommands(SurveyService surveyService, LocaleHelper localeHelper) {
        this.surveyService = surveyService;
        this.localeHelper = localeHelper;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption({"surname"}) String surname, @ShellOption({"name"}) String name) {
        this.user = new Person(surname, name);
        return this.localeHelper.getMessage("survey.welcome-text", surname, name);
    }


    @ShellMethod(value = "Start survey", key = {"s", "start"})
    public String startSurvey() {
        this.survey = this.surveyService.createSurvey(this.user);
        return this.localeHelper.getMessage("survey.started-text");
    }

    @ShellMethod(value = "Get current question", key = {"q", "question"})
    public String getCurrentQuestion() {
        return this.survey.getCurrentQuestion().getQuestionText();
    }

    @ShellMethod(value = "Answer to current question", key = {"a", "answer"})
    public String answerToCurrentQuestion(@ShellOption({"answer"}) String answer) {
        this.surveyService.answerToCurrentSurveyQuestion(this.survey, answer);
        return this.localeHelper.getMessage("survey.answer-accepted");
    }

    @ShellMethod(value = "Get result", key = {"r", "result"})
    public String getResult() {
        return this.localeHelper.getMessage("survey.result-text",
                this.user.getSurname(),
                this.user.getName(),
                this.survey.getScore(),
                this.localeHelper.getMessage(String.format("survey.pass.%s", survey.isPassed()))
        );
    }


    @ShellMethodAvailability({"start"})
    private Availability isStartAvailable() {
        return this.user == null ? Availability.unavailable(this.localeHelper.getMessage("survey.availability.login")) : Availability.available();
    }

    private Availability isSurveyAvailable() {
        return this.survey == null ? Availability.unavailable(this.localeHelper.getMessage("survey.availability.start")) : Availability.available();
    }

    @ShellMethodAvailability({"question", "answer"})
    private Availability isQuestionsAvailable() {
        return this.composeAvailabilities(new Supplier[]{
                this::isStartAvailable,
                this::isSurveyAvailable,
                () -> this.survey.isFinished() ? Availability.unavailable(this.localeHelper.getMessage("survey.availability.restart")) : Availability.available()

        });
    }

    @ShellMethodAvailability({"result"})
    private Availability isResultAvailable() {
        return this.composeAvailabilities(new Supplier[]{
                this::isStartAvailable,
                this::isSurveyAvailable,
                () -> this.survey.isFinished() ?  Availability.available() : Availability.unavailable(this.localeHelper.getMessage("survey.availability.finish"))
        });
    }

    private Availability composeAvailabilities(Supplier<Availability>[] availabilitySuppliers) {
        for (Supplier<Availability> availabilitySupplier : availabilitySuppliers) {
            var result = availabilitySupplier.get();
            if (!result.isAvailable()) return result;
        }
        return Availability.available();
    }
}
