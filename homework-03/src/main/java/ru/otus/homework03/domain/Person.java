package ru.otus.homework03.domain;

public class Person {
    private final String surname;
    private final String name;

    public Person(String surname, String name) {
        this.surname = surname;
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }
}
