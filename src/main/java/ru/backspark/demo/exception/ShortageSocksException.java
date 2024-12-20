package ru.backspark.demo.exception;

public class ShortageSocksException extends RuntimeException{
    public ShortageSocksException() {
        super("Shortage of socks in stock");
    }
}
