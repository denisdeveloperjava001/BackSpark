package ru.backspark.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocksOutcomeParameters {
    private String color;
    private int cottonPercentage;
    private int amount;
}
