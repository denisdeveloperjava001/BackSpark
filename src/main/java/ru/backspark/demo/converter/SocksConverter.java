package ru.backspark.demo.converter;

import ru.backspark.demo.model.Socks;
import ru.backspark.demo.model.SocksDto;

public class SocksConverter {

    public static SocksDto toDto(Socks socks) {
        return new SocksDto(socks.getId(), socks.getColor(), socks.getCottonPercentage(), socks.getAmount());
    }

}
