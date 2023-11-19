package app.drive.service;

import app.drive.model.dto.PassengerRequestDto;
import app.drive.model.entity.PassengerEntity;
import app.drive.repository.PassengerRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
@Log4j2
public class PassengerService {

    @NonNull
    private final PassengerRepository passengerRepository;

    public PassengerEntity createPassenger(PassengerEntity newPassenger) throws Exception {
        try {
            return passengerRepository.save(newPassenger);
        } catch (Exception e) {
            log.error("There is already an user with same value for email. ");
        }

        return null;
    }

    public PassengerEntity login(String email, String password) {
        return passengerRepository.findByEmailAndPassword(email, password);
    }

    public void updatePassengerLocation(PassengerRequestDto passengerRequestDto) {
        passengerRepository.updatePassengerLocation(passengerRequestDto);
    }
}
