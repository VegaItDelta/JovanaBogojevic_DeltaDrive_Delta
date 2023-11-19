package app.drive.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private Long id;

    private String brand;

    private String driverFirstName;

    private String driverLastName;

    private double latitude;

    private double longitude;

    private String startingPrice;

    private String pricePerKm;

    private boolean isAvailable;
}
