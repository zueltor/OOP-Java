package ru.nsu.g.mustafin.phrases;

public class StreamMain {
    public static void main(String[] args) {
        IPhrases.process(args, new StreamPhrases());
    }
}

