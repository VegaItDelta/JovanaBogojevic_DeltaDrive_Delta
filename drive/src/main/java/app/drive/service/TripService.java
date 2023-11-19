package app.drive.service;


import app.drive.model.dto.LocationDto;
import app.drive.model.entity.PassengerEntity;
import app.drive.model.entity.TripEntity;
import app.drive.model.entity.TripRequestStatus;
import app.drive.model.entity.VehicleEntity;
import app.drive.repository.PassengerRepository;
import app.drive.repository.TripRepository;
import app.drive.util.DistanceCalculator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Log4j2
@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    private final PassengerRepository passengerRepository;

    private static final double EARTH_RADIUS = 6371.0;

    public TripEntity createTrip(VehicleEntity selectedVehicle, Long passengerId) {
        var passenger = findPassengerById(passengerId);

        if (passenger.isEmpty()) {
            log.error("Passenger not found with ID: {}", passengerId);
            return null;
        }

        var userLocation = new LocationDto(passenger.get().getCurrentLatitude(), passenger.get().getCurrentLongitude());
        var totalDistance = DistanceCalculator.calculateHaversineDistance
                (userLocation, selectedVehicle.getLatitude(), selectedVehicle.getLongitude())/1000.0; // in km
        var totalPrice = calculateTripPrice(totalDistance, selectedVehicle);

        var newTrip = TripEntity.builder()
                .vehicleEntity(selectedVehicle)
                .passengerEntity(passenger.get())
                .totalDistance(totalDistance)
                .totalPrice(totalPrice)
                .status(TripRequestStatus.ACCEPTED)
                .build();

        return tripRepository.save(newTrip);
    }

    private Optional<PassengerEntity> findPassengerById(Long passengerId) {
        return passengerRepository.findById(passengerId);
    }

    private double calculateTripPrice(double totalDistance, VehicleEntity vehicleEntity) {
        var startingPriceWithoutCurrency = vehicleEntity.getStartingPrice().replaceAll("[^0-9.]", "");
        var priceWithoutCurrency = vehicleEntity.getPricePerKm().replaceAll("[^0-9.]", "");
        var distanceInKm = totalDistance / 1000.0;

        return Double.parseDouble(startingPriceWithoutCurrency) + (Double.parseDouble(priceWithoutCurrency) * distanceInKm);
    }
}



