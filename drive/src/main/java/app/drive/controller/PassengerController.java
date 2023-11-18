package app.drive.controller;

import app.drive.model.dto.LoginDto;
import app.drive.model.dto.PassengerDto;
import app.drive.model.mapper.PassengerMapper;
import app.drive.service.PassengerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping(value="/api/passengers")
public class PassengerController {

    @NonNull
    private final PassengerService passengerService;

    @PostMapping(
            value = "/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerDto> userRegistration(@RequestBody PassengerDto passengerDto) throws Exception {
        var newPassenger = PassengerMapper.toPassengerEntity(passengerDto);
        var createdPassenger = passengerService.createPassenger(newPassenger);
        var createdPassengerDto = PassengerMapper.toPassengerDto(createdPassenger);

        return new ResponseEntity<>(createdPassengerDto, HttpStatus.CREATED);
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PassengerDto> userLogin(@RequestBody LoginDto loginCredentials) {
        var foundPassenger = passengerService.login(loginCredentials.getEmail(), loginCredentials.getPassword());

        if(foundPassenger == null) {
            log.error("Invalid credentials. ");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var passenger = PassengerMapper.toPassengerDto(foundPassenger);
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }
}
