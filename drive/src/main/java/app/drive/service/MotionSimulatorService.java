package app.drive.service;

import app.drive.model.dto.LocationDto;
import app.drive.model.entity.VehicleEntity;
import app.drive.repository.PassengerRepository;
import app.drive.repository.VehicleRepository;
import app.drive.util.DistanceCalculator;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
@Log4j2
public class MotionSimulatorService {

    private static final double VEHICLE_SPEED_KMH = 60.0; // Vehicle speed in km/h
    private static final int UPDATE_INTERVAL = 5; // Update interval in seconds

    @NonNull
    private final VehicleRepository vehicleRepository;

    public void simulateDrive(VehicleEntity vehicle, double passengerLatitude,
                                     double passengerLongitude, double destinationLatitude, double destinationLongitude) {

        var vehicleDistanceFromPassenger = DistanceCalculator.calculateHaversineDistance(new LocationDto(vehicle.getLatitude(),
                vehicle.getLongitude()), passengerLatitude, passengerLongitude);

        var passengerDistanceFromDestination = DistanceCalculator.calculateHaversineDistance(new LocationDto(passengerLatitude,
                passengerLongitude), destinationLatitude, destinationLongitude);

        double remainingDistance = vehicleDistanceFromPassenger;

        while (remainingDistance > 0) {
            var distanceCovered = VEHICLE_SPEED_KMH * (UPDATE_INTERVAL / 3600.0);
            remainingDistance -= distanceCovered;
        }

        // vehicle has reached the passenger, update vehicle location to passenger's
        updateVehicleLocation(vehicle, passengerLatitude, passengerLongitude);
        log.info("Updated vehicle location to passenger's");

        remainingDistance = passengerDistanceFromDestination;

        while(remainingDistance > 0) {
            var distanceCovered = VEHICLE_SPEED_KMH * (UPDATE_INTERVAL / 3600.0);
            remainingDistance -= distanceCovered;
        }

        // vehicle has reached the destination, update vehicle location to destination
        updateVehicleLocation(vehicle, destinationLatitude, destinationLongitude);
        log.info("Updated vehicle location to destination location");
    }

    private void updateVehicleLocation(VehicleEntity vehicle, double latitude, double longitude) {
        vehicleRepository.updateVehicleLocation(vehicle.getId(), latitude, longitude);
    }
}
