package ru.backspark.demo.exception;

public class IncorrectDataFormatException extends RuntimeException {
    public IncorrectDataFormatException() {
        super("Incorrect data format");
    }
}
