package ru.otus.homework09.exceptions;

public class BaseLibraryException extends RuntimeException{
    public BaseLibraryException(){
        super();
    }
    public BaseLibraryException(RuntimeException ex){
        super(ex);
    }
}
