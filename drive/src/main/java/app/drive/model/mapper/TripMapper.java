package app.drive.model.mapper;

import app.drive.model.dto.TripDto;
import app.drive.model.entity.PassengerEntity;
import app.drive.model.entity.TripEntity;
import app.drive.model.entity.VehicleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TripMapper {

    public TripMapper() {

    }

    public static TripDto toTripDto(TripEntity tripEntity) {
        return TripDto.builder()
                .id(tripEntity.getId())
                .totalDistance(tripEntity.getTotalDistance())
                .totalPrice(tripEntity.getTotalPrice())
                .build();
    }

    public static List<TripDto> toTripDtoList(List<TripEntity> trips) {
        return trips.stream().map(TripMapper::toTripDto).collect(Collectors.toList());
    }

    public static TripDto toDto(TripEntity tripEntity, VehicleEntity vehicleEntity, PassengerEntity passengerEntity) {
        return TripDto.builder()
                .id(tripEntity.getId())
                .totalDistance(tripEntity.getTotalDistance())
                .totalPrice(tripEntity.getTotalPrice())
                .startingLatitude(passengerEntity.getCurrentLatitude())
                .startingLongitude(passengerEntity.getDesiredLongitude())
                .targetLatitude(passengerEntity.getDesiredLatitude())
                .targetLongitude(passengerEntity.getDesiredLongitude())
                .driverFirstName(vehicleEntity.getDriverFirstName())
                .driverLastName(vehicleEntity.getDriverLastName())
                .build();
    }

    public static List<TripDto> toDtoList(List<TripEntity> trips) {
        return trips.stream().map(TripMapper::toTripDto).collect(Collectors.toList());
    }
}
