package app.drive.model.mapper;

import app.drive.model.dto.VehicleDto;
import app.drive.model.entity.VehicleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleMapper {

    private VehicleMapper(){

    }

    public static VehicleDto toVehicleDto(VehicleEntity vehicleEntity) {
        return VehicleDto.builder()
                .id(vehicleEntity.getId())
                .brand(vehicleEntity.getBrand())
                .driverFirstName(vehicleEntity.getDriverFirstName())
                .driverLastName(vehicleEntity.getDriverLastName())
                .latitude(vehicleEntity.getLatitude())
                .longitude(vehicleEntity.getLongitude())
                .pricePerKm(vehicleEntity.getPricePerKm())
                .startingPrice(vehicleEntity.getStartingPrice())
                .isAvailable(vehicleEntity.isAvailable())
                .build();
    }

    public static List<VehicleDto> toVehicleDtoList(List<VehicleEntity> vehicleEntities) {
        return vehicleEntities.stream().map(VehicleMapper::toVehicleDto).collect(Collectors.toList());
    }

}
