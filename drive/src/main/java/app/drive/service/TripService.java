package app.drive.service;


import app.drive.model.dto.LocationDto;
import app.drive.model.dto.TripDto;
import app.drive.model.entity.PassengerEntity;
import app.drive.model.entity.TripEntity;
import app.drive.model.entity.TripRequestStatus;
import app.drive.model.entity.VehicleEntity;
import app.drive.model.mapper.TripMapper;
import app.drive.repository.PassengerRepository;
import app.drive.repository.TripRepository;
import app.drive.util.DistanceCalculator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
                (userLocation, selectedVehicle.getLatitude(), selectedVehicle.getLongitude()); // in km
        var totalPrice = calculateTripPrice(totalDistance, selectedVehicle);

        var newTrip = TripEntity.builder()
                .vehicleEntity(selectedVehicle)
                .passengerEntity(passenger.get())
                .totalDistance(totalDistance)
                .totalPrice(totalPrice)
                .status(TripRequestStatus.ACCEPTED)
                .startingLongitude(passenger.get().getCurrentLongitude())
                .startingLatitude(passenger.get().getCurrentLatitude())
                .targetLatitude(passenger.get().getDesiredLatitude())
                .targetLongitude(passenger.get().getDesiredLongitude())
                .driverFirstName(selectedVehicle.getDriverFirstName())
                .driverLastName(selectedVehicle.getDriverLastName())
                .build();

        return tripRepository.save(newTrip);
    }

    private Optional<PassengerEntity> findPassengerById(Long passengerId) {
        return passengerRepository.findById(passengerId);
    }

    private double calculateTripPrice(double totalDistance, VehicleEntity vehicleEntity) {
        var startingPriceWithoutCurrency = vehicleEntity.getStartingPrice().replaceAll("[^0-9.]", "");
        var priceWithoutCurrency = vehicleEntity.getPricePerKm().replaceAll("[^0-9.]", "");

        return Double.parseDouble(startingPriceWithoutCurrency) + (Double.parseDouble(priceWithoutCurrency) * totalDistance);
    }

    public List<TripDto> getTrips(Long passengerId) {
        var foundTrips = tripRepository.findByPassengerEntityId(passengerId);
        var tripDtos = new ArrayList<TripDto>();

        for (var trip: foundTrips) {
            var vehicle = trip.getVehicleEntity();
            var passenger = trip.getPassengerEntity();

            var dto = TripMapper.toDto(trip, vehicle, passenger);
            tripDtos.add(dto);
        }

        return tripDtos;
    }
}



