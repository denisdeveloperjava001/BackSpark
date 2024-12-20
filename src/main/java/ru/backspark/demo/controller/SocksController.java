package ru.backspark.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.backspark.demo.converter.SocksConverter;
import ru.backspark.demo.model.*;
import ru.backspark.demo.service.SocksService;

import java.util.UUID;

@RestController("/api/socks")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class SocksController {

    private final SocksService socksService;

    @PostMapping("/income")
    public SocksDto income(@RequestBody SocksIncomeParameters incomeParameters) {
        log.info("Request received 'income'. Request parameters:"
                + " Color " + incomeParameters.getColor() + ","
                + " Cotton percentage " +  incomeParameters.getCottonPercentage() + ","
                + " Amount " + incomeParameters.getAmount() + ".");

        Socks socks = socksService.income(incomeParameters);
        return SocksConverter.toDto(socks);
    }

    @PostMapping("/outcome")
    public SocksDto outcome(@RequestBody SocksOutcomeParameters outcomeParameters) {
        log.info("Request received 'outcome'. Request parameters:"
                + " Color " + outcomeParameters.getColor() + ","
                + " Cotton percentage " +  outcomeParameters.getCottonPercentage() + ","
                + " Amount " + outcomeParameters.getAmount() + ".");

        Socks socks = socksService.outcome(outcomeParameters);
        return SocksConverter.toDto(socks);
    }

    @GetMapping
    public long get(@RequestParam String color,
                    @RequestParam int cottonPercentage,
                    @RequestParam ComparisonOperator comparisonOperator) {
        log.info("Request received 'get'. Request parameters:"
                + " Color " + color + ","
                + " Cotton percentage " +  cottonPercentage + ","
                + " Comparison Operator " + comparisonOperator + ".");

        return socksService.get(color, cottonPercentage, comparisonOperator);
    }

    @PutMapping("/{id}")
    public SocksDto update(@PathVariable UUID id,
                           @RequestBody SocksUpdateParameters updateParameters) {
        log.info("Request received 'update'. Request parameters:"
                + " Id " + id + ","
                + " Color " + updateParameters.getColor() + ","
                + " Cotton percentage " +  updateParameters.getCottonPercentage() + ","
                + " Amount " + updateParameters.getAmount() + ".");

        Socks socks = socksService.update(id, updateParameters);
        return SocksConverter.toDto(socks);

    }

    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upLoadCsv(@RequestParam("file") MultipartFile file){
        log.info("Request received 'upload CSV file'");

        socksService.upLoadCsv(file);
    }
}
