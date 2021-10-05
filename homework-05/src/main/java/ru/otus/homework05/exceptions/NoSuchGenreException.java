package ru.otus.homework05.exceptions;

public class NoSuchGenreException extends BaseLibraryException{
    public NoSuchGenreException(RuntimeException ex) {
        super(ex);
    }
}