package app.drive.config;

import app.drive.model.entity.VehicleEntity;
import app.drive.repository.VehicleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Component
@Log4j2
public class DatabaseInitializer implements ApplicationRunner {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public DatabaseInitializer(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        long startTime = System.currentTimeMillis();
        var random = new Random();
        double availabilityThreshold = 0.5; // 50% of the vehicles will be available
        int batchSize = 10000;

        Iterator<VehicleEntity> vehicleIterator = vehicleRepository.findAll().iterator();
        List<VehicleEntity> batch = new ArrayList<>();

        while (vehicleIterator.hasNext()) {
            VehicleEntity vehicle = vehicleIterator.next();
            boolean isAvailable = random.nextDouble() < availabilityThreshold;
            vehicle.setAvailable(isAvailable);
            batch.add(vehicle);

            if (batch.size() % batchSize == 0 || !vehicleIterator.hasNext()) {
                vehicleRepository.saveAll(batch);
                batch.clear();
            }
        }

        long endTime = System.currentTimeMillis();
        log.info("DatabaseInitializer execution time: {} ms", endTime - startTime);
    }
}