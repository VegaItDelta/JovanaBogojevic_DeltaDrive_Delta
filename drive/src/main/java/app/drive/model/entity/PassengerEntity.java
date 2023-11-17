package app.drive.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Date birthday;

    private double currentLongitude;

    private double currentLatitude;

    private double desiredLongitude;

    private double desiredLatitude;

    @OneToMany(mappedBy = "passengerEntity")
    private List<TripEntity> tripHistory;

}
