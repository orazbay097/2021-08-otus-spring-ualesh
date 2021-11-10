package ru.otus.homework07.exceptions;

public class BaseLibraryException extends RuntimeException{
    public BaseLibraryException(){
        super();
    }
    public BaseLibraryException(RuntimeException ex){
        super(ex);
    }
}
