package ru.backspark.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.backspark.demo.model.Socks;

import java.util.List;
import java.util.UUID;

@Repository
public interface SocksRepository extends JpaRepository<Socks, UUID> {

    Socks findByColorAndCottonPercentage (String color, int cottonPercentage);

    List<Socks> findAllByColorAndCottonPercentage(String color, int cottonPercentage);

    List<Socks> findAllByColorAndCottonPercentageGreaterThan(String color, int cottonPercentage);

    List<Socks> findAllByColorAndCottonPercentageLessThan(String color, int cottonPercentage);

}
