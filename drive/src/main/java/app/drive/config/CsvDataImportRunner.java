package app.drive.config;

import app.drive.service.CsvImportService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class CsvDataImportRunner implements ApplicationRunner {

    private final CsvImportService csvImportService;
    private final ResourceLoader resourceLoader;

    public CsvDataImportRunner(CsvImportService csvImportService, ResourceLoader resourceLoader) {
        this.csvImportService = csvImportService;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        String csvFilePath = "classpath:import/delta-drive.csv";
        var resource = resourceLoader.getResource(csvFilePath);

        // Get the absolute file path from the resource
        String absoluteFilePath = resource.getFile().getAbsolutePath();

        csvImportService.importCsvData(absoluteFilePath);
    }
}