package app.drive.config;

import app.drive.service.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvDataImportRunner implements ApplicationRunner {

    @Autowired
    private CsvImportService csvImportService;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        String csvFilePath = "C:\\Users\\PC\\Desktop\\drive\\drive\\src\\main\\resources\\import\\delta-drive.csv";
        csvImportService.importCsvData(csvFilePath);
    }
}