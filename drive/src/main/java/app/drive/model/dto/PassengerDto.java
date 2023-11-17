package app.drive.model.dto;

import app.drive.model.entity.TripEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PassengerDto {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Date birthday;

    private List<TripEntity> rideHistory;
}
