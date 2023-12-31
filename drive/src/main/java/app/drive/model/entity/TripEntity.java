package app.drive.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passengerEntity;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
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
