package com.lafis.infra.temperature;

import com.lafis.core.entity.day.DailyTemperatureData;
import com.lafis.core.ports.DailyTemperatureReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DailyTemperatureProvider implements DailyTemperatureReader {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Map<LocalDate, DailyTemperatureData> loadAllDailyTemperatureData(String filePath) {
        Map<LocalDate, DailyTemperatureData> temperaturesByDate = new HashMap<>();

        String[] fileHeader = {"Dia", "Temperatura Mínima (C)", "Temperatura Média (C)", "Temperatura Máxima (C)"};

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(fileHeader)
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .get();

        try (Reader in = new FileReader(filePath); CSVParser parser = new CSVParser(in, csvFormat)){
            for (CSVRecord record : parser) {
                try {
                    LocalDate date = LocalDate.parse(record.get("Dia"), DATE_FORMATTER);
                    double maxTemp = Double.parseDouble(record.get("Temperatura Máxima (C)"));
                    double temperature = Double.parseDouble(record.get("Temperatura Média (C)"));

                    DailyTemperatureData data = new DailyTemperatureData(date, maxTemp, temperature);
                    temperaturesByDate.put(date, data);

                } catch (NumberFormatException e) {
                    System.err.println("Skipping row due to number format error: " + record.toString() + " - " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Skipping row due to parsing error: " + record.toString() + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temperaturesByDate;
    }
}
