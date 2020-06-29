package ru.nsu.g.mustafin.chat.utils.exceptions;

public class UnknownResponseException extends Exception{
    public UnknownResponseException(String text){
        super(text);
    }
}
