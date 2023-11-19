package app.drive.model.dto;

import app.drive.model.entity.PassengerEntity;
import app.drive.model.entity.TripRequestStatus;
import app.drive.model.entity.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private Long id;

    private PassengerEntity passengerEntity;

    private VehicleEntity vehicleEntity;

    private TripRequestStatus status;

    private double totalDistance;

    private double totalPrice;

    private double startingLongitude;

    private double startingLatitude;

    private double targetLongitude;

    private double targetLatitude;

    private String driverFirstName;

    private String driverLastName;
}
