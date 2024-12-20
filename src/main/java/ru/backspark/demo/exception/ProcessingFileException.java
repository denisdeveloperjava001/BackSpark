package ru.backspark.demo.exception;

public class ProcessingFileException extends RuntimeException {
    public ProcessingFileException() {
        super("Error while processing file");
    }
}
