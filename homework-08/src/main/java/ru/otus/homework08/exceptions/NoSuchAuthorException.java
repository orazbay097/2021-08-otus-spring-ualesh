package ru.otus.homework08.exceptions;

public class NoSuchAuthorException extends BaseLibraryException{
    public NoSuchAuthorException() {
        super();
    }
    public NoSuchAuthorException(RuntimeException ex) {
        super(ex);
    }
}