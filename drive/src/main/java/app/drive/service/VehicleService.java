package app.drive.service;

import app.drive.model.dto.LocationDto;
import app.drive.model.dto.PassengerRequestDto;
import app.drive.model.entity.VehicleEntity;
import app.drive.repository.PassengerRepository;
import app.drive.repository.VehicleRepository;
import app.drive.util.DistanceCalculator;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Data
@Log4j2
@Service
@RequiredArgsConstructor
public class VehicleService {

    @NonNull
    private final VehicleRepository vehicleRepository;

    @NonNull
    private final TripService tripService;

    @NonNull
    private final PassengerService passengerService;

    @NonNull
    private final MotionSimulatorService motionSimulatorService;

    @NonNull
    private final PassengerRepository passengerRepository;

    private static final double EARTH_RADIUS = 6371.0;
    private static final Random random = new Random();

    public List<VehicleEntity> findNearestVehicles(PassengerRequestDto passengerRequestDto) {
        var currentLocation = passengerRequestDto.getCurrentLocation();
        var targetDestination = passengerRequestDto.getTargetLocation();

        updatePassengerLocation(passengerRequestDto);

        var vehicles = vehicleRepository.findAvailableVehicles();

        return vehicles.stream()
                .sorted(Comparator.comparingDouble(vehicle ->
                        calculateCombinedDistance(currentLocation, targetDestination, vehicle.getLatitude(), vehicle.getLongitude())))
                .limit(10)
                .collect(Collectors.toList());
    }

    private void updatePassengerLocation(PassengerRequestDto passengerRequestDto) {
        // everytime a passenger sends a new request for a ride, update their location
        passengerService.updatePassengerLocation(passengerRequestDto);
    }

    private double calculateCombinedDistance(LocationDto userLocation, LocationDto targetDestination, double latitude, double longitude) {
        double distanceFromUser = DistanceCalculator.calculateHaversineDistance(userLocation, latitude, longitude);
        double distanceFromDestination = DistanceCalculator.calculateHaversineDistance(targetDestination, latitude, longitude);

        return distanceFromUser + distanceFromDestination;
    }

    public boolean handleBooking(Long vehicleId, Long passengerId) {
        var isBooked = false;
        var foundVehicle = vehicleRepository.findById(vehicleId);

        if (foundVehicle.isEmpty()) {
            log.warn("There is no vehicles with provided vehicleId");
            return false; // Vehicle not found
        }

        var selectedVehicle = foundVehicle.get();

        if (simulateDriverAcceptance()) {
            tripService.createTrip(selectedVehicle, passengerId);
            selectedVehicle.setAvailable(false);
            simulateVehicleMovement(selectedVehicle, passengerId);
            isBooked = true;
        }

        return isBooked;
    }

    private boolean simulateDriverAcceptance() {
        return random.nextDouble() > 0.25;
    }

    private void simulateVehicleMovement(VehicleEntity vehicle, Long passengerId) {
        var passenger = passengerRepository.findById(passengerId).get();

        motionSimulatorService.simulateDrive(vehicle, passenger.getCurrentLatitude(), passenger.getCurrentLongitude(),
                passenger.getDesiredLatitude(), passenger.getDesiredLongitude());
    }
}
