package ru.backspark.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class SocksDto {
    private UUID id;
    private String color;
    private int cottonPercentage;
    private int amount;
}
