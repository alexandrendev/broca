package com.lafis.infra.repository;

import com.lafis.core.entity.day.DailySimulationData;
import com.lafis.core.ports.DailySimulationDataRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class DailySimulationDataCsvRepository implements DailySimulationDataRepository {
    private CSVPrinter csvPrinter;
    private FileWriter fileWriter;
    private final String OUTPUT_DIRECTORY = "output";
    private String fileName;

    public DailySimulationDataCsvRepository() throws IOException {
        java.nio.file.Files.createDirectories(Paths.get(OUTPUT_DIRECTORY));
    }


    @Override
    public void initialize(String fileName) {
        this.fileName = "simulation_data_" + fileName + ".csv";

        try{
            String fullPath = Paths.get(OUTPUT_DIRECTORY, this.fileName).toString();
            fileWriter = new FileWriter(fullPath);

            csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder()
                    .setHeader("Day", "Hosts", "Infected", "Parasitoids").get());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void append(DailySimulationData data) {
        if (csvPrinter == null) {
            throw new IllegalStateException("Repository not initialized. Call initialize() first.");
        }
        try {
            csvPrinter.printRecord(data.hosts(), data.infected(), data.parasitoid());
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error appending data to CSV for " + fileName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void saveAll(List<DailySimulationData> data) {
        if (csvPrinter == null) {
            throw new IllegalStateException("Repository not initialized. Call initialize() first.");
        }
        try {
            for (DailySimulationData record : data) {
                csvPrinter.printRecord(record.hosts(), record.infected(), record.parasitoid());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error saving all data to CSV for " + fileName + ": " + e.getMessage(), e);
        }
    }


    @Override
    public void close() {
        try {
            if (csvPrinter != null) {
                csvPrinter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing CSV repository for " + fileName + ": " + e.getMessage(), e);
        } finally {
            csvPrinter = null;
            fileWriter = null;
            fileName = null;
        }
    }
}
