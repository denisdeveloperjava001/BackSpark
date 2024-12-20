package ru.backspark.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Socks {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "color")
    private String color;

    @Column(name = "cotton_percentage")
    private int cottonPercentage;

    @Column(name = "amount")
    private int amount;

}
