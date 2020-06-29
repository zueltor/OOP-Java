package ru.nsu.g.mustafin.chat.utils.exceptions;

public class ServerErrorException extends Exception {
    public ServerErrorException(String text){
        super(text);
    }
}
