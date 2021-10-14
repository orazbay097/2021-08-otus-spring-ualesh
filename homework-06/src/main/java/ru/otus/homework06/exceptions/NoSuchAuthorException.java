package ru.otus.homework06.exceptions;

public class NoSuchAuthorException extends BaseLibraryException{
    public NoSuchAuthorException(RuntimeException ex) {
        super(ex);
    }
}