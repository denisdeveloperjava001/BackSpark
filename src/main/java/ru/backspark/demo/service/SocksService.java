package ru.backspark.demo.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.backspark.demo.model.SocksOutcomeParameters;
import ru.backspark.demo.exception.IncorrectDataFormatException;
import ru.backspark.demo.exception.ProcessingFileException;
import ru.backspark.demo.exception.ShortageSocksException;
import ru.backspark.demo.exception.SocksNotFoundException;
import ru.backspark.demo.model.*;
import ru.backspark.demo.repository.SocksRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SocksService {

    private final SocksRepository socksRepository;

    public Socks income(SocksIncomeParameters socksIncomeParameters) {
        Socks existingSocks = socksRepository.findByColorAndCottonPercentage(
                socksIncomeParameters.getColor(),
                socksIncomeParameters.getCottonPercentage()
        );

        if(existingSocks == null) {
            Socks socks = new Socks();
            socks.setColor(socksIncomeParameters.getColor());
            socks.setAmount(socksIncomeParameters.getAmount());
            socks.setCottonPercentage(socksIncomeParameters.getCottonPercentage());
            return socksRepository.save(socks);
        } else {
            existingSocks.setAmount(existingSocks.getAmount() + socksIncomeParameters.getAmount());
            socksRepository.save(existingSocks);
            return existingSocks;
        }
    }

    public Socks outcome(SocksOutcomeParameters outcomeParameters) {
        Socks socks = socksRepository.findByColorAndCottonPercentage(outcomeParameters.getColor(), outcomeParameters.getCottonPercentage());
        int amount = socks.getAmount();
        if(amount >= outcomeParameters.getAmount()) {
            socks.setAmount(amount - outcomeParameters.getAmount());
            socksRepository.save(socks);
            return socks;
        } else {
            throw new ShortageSocksException();
        }
    }

    public long get(String color, int cottonPercentage, ComparisonOperator comparisonOperator) {
        List<Socks> socksList = new ArrayList<>();
        long socksSummarizedAmount = 0;

        if (comparisonOperator.equals(ComparisonOperator.equal)) {
            socksList = socksRepository.findAllByColorAndCottonPercentage(color, cottonPercentage);
        } else if (comparisonOperator.equals(ComparisonOperator.lessThan)) {
            socksList = socksRepository.findAllByColorAndCottonPercentageLessThan(color, cottonPercentage);
        } else if (comparisonOperator.equals(ComparisonOperator.moreThan)) {
            socksList = socksRepository.findAllByColorAndCottonPercentageGreaterThan(color, cottonPercentage);
        } else {
            throw new IncorrectDataFormatException();
        }

        for (Socks socks: socksList) {
            socksSummarizedAmount += socks.getAmount();
        }

        return socksSummarizedAmount;
    }

    public Socks update(UUID id, SocksUpdateParameters updateParameters) {
        Socks socks = socksRepository.findById(id).orElseThrow(SocksNotFoundException::new);
        socks.setCottonPercentage(updateParameters.getCottonPercentage());
        socks.setAmount(updateParameters.getAmount());
        socks.setColor(updateParameters.getColor());
        socksRepository.save(socks);
        return socks;
    }

    public void upLoadCsv(MultipartFile file) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build());
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                SocksIncomeParameters socksIncomeParameters = new SocksIncomeParameters(
                        csvRecord.get("Color"),
                        Integer.parseInt(csvRecord.get("Cotton percentage")),
                        Integer.parseInt(csvRecord.get("Amount"))
                );
                income(socksIncomeParameters);
            }
        } catch (IOException e) {
            throw new ProcessingFileException();
        }
    }

}
