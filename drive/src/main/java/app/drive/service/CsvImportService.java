package app.drive.service;

import app.drive.model.entity.VehicleEntity;
import app.drive.repository.VehicleRepository;
import com.opencsv.CSVReader;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class CsvImportService {

    @Autowired
    private VehicleRepository vehicleRepository;

    private static final int BATCH_SIZE = 10000;


    // Needs optimisation, takes approximately 8 sec
    @Transactional
    public void importCsvData(String csvFilePath) {
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            long startTime = System.currentTimeMillis();
            List<VehicleEntity> vehiclesBatch = new ArrayList<>();
            String[] nextLine;
            boolean firstRow = true;

            while ((nextLine = reader.readNext()) != null) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }

                VehicleEntity vehicleEntity = new VehicleEntity();
                vehicleEntity.setId(Long.getLong(nextLine[0]));
                vehicleEntity.setBrand(nextLine[1]);
                vehicleEntity.setDriverFirstName(nextLine[2]);
                vehicleEntity.setDriverLastName(nextLine[3]);
                vehicleEntity.setLatitude(Double.parseDouble(nextLine[4]));
                vehicleEntity.setLongitude(Double.parseDouble(nextLine[5]));
                vehicleEntity.setPricePerKm(nextLine[6]);
                vehicleEntity.setStartingPrice(nextLine[7]);

                vehiclesBatch.add(vehicleEntity);

                if (vehiclesBatch.size() % BATCH_SIZE == 0) {
                    vehicleRepository.saveAll(vehiclesBatch);
                    vehiclesBatch.clear();
                }
            }

            vehicleRepository.saveAll(vehiclesBatch);
            long endTime = System.currentTimeMillis();
            log.info("CSVImport execution time: {} ms", endTime - startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
