package ru.backspark.demo.exception;

public class SocksNotFoundException extends RuntimeException {
    public SocksNotFoundException() {
        super("Socks not found in stock");
    }
}
