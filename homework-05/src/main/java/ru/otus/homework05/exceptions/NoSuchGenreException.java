package ru.otus.homework05.exceptions;

public class NoSuchGenreException extends RuntimeException{
    public NoSuchGenreException(RuntimeException ex) {
        super(ex);
    }
}
