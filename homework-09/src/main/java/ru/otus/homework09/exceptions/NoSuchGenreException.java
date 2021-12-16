package ru.otus.homework09.exceptions;

public class NoSuchGenreException extends BaseLibraryException{
    public NoSuchGenreException(){super();}
    public NoSuchGenreException(RuntimeException ex) {
        super(ex);
    }
}