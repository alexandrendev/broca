package com.lafis.infra.repository;

import com.lafis.core.entity.day.DailySimulationData;
import com.lafis.core.ports.DailySimulationDataRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    public void initialize(String fileName, boolean writeMode) {
        this.fileName = "simulation_data_" + fileName + ".csv";
        String fullPath = Paths.get(OUTPUT_DIRECTORY, this.fileName).toString();

        if (writeMode) {
            try {
                fileWriter = new FileWriter(fullPath); // sobrescreve
                csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder()
                        .setHeader("Day", "Hosts", "Infected", "Parasitoids").get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void append(DailySimulationData data) {
        if (csvPrinter == null) {
            throw new IllegalStateException("Repository not initialized. Call initialize() first.");
        }
        try {
            csvPrinter.printRecord(data.date().toString(), data.hosts(), data.infected(), data.parasitoid());
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error appending data to CSV for " + fileName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<DailySimulationData> getData() {

        if (fileName == null) {
            System.err.println("Repositório não inicializado. Chame initialize() antes de getData().");
            return new ArrayList<>();
        }

        List<DailySimulationData> dataList = new ArrayList<>();
        String fullPath = Paths.get(OUTPUT_DIRECTORY, this.fileName).toString();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    try {
                        LocalDate date = LocalDate.parse(parts[0].trim());
                        int hosts = Integer.parseInt(parts[1].trim());
                        int infected = Integer.parseInt(parts[2].trim());
                        int parasitoid = Integer.parseInt(parts[3].trim());

                        dataList.add(new DailySimulationData(date, hosts, infected, parasitoid));
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao parsear número na linha do CSV: " + line + " - " + e.getMessage());
                    } catch (DateTimeParseException e) {
                        System.err.println("Erro ao parsear data na linha do CSV: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Linha com formato inválido no CSV (esperado 4 colunas): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV " + fullPath + ": " + e.getMessage());

             throw new RuntimeException("Falha ao ler dados do CSV", e);
        }
        return dataList;
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
