package ru.otus.homework07.exceptions;

public class NoSuchAuthorException extends BaseLibraryException{
    public NoSuchAuthorException() {
        super();
    }
    public NoSuchAuthorException(RuntimeException ex) {
        super(ex);
    }
}