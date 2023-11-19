package app.drive.config;

import app.drive.model.entity.VehicleEntity;
import app.drive.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public DatabaseInitializer(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        var random = new Random();
        double availabilityThreshold = 0.3;

        Iterable<VehicleEntity> vehicles = vehicleRepository.findAll();

        for (var vehicle : vehicles) {
            boolean isAvailable = random.nextDouble() < availabilityThreshold;
            vehicle.setAvailable(isAvailable);
            vehicleRepository.save(vehicle);
        }
    }
}