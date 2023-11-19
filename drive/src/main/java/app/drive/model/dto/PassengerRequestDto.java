package app.drive.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequestDto {

    private Long passengerId;
    private LocationDto currentLocation;
    private LocationDto targetLocation;
}
