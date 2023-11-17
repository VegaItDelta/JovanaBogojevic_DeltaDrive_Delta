package app.drive;

import app.drive.model.entity.PassengerEntity;
import app.drive.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@DataJpaTest
public class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    public void givenPassengerEntity_whenSave_thenNewEntityIsCreated() {
        var passenger = PassengerEntity.builder()
                .id(new Random().nextLong())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .birthday(Date.from(Instant.now()))
                .password("secretpassword")
                .build();

        passengerRepository.save(passenger);

        var foundPassenger = passengerRepository.findByEmailAndPassword(passenger.getEmail(), passenger.getPassword());

        assertThat(foundPassenger).isNotNull();
        assertThat(foundPassenger.getEmail()).isEqualTo(passenger.getEmail());
        assertThat(foundPassenger.getPassword()).isEqualTo(passenger.getPassword());
    }

}
