package app.drive.controller;

import app.drive.model.dto.TripDto;
import app.drive.model.entity.TripEntity;
import app.drive.model.mapper.TripMapper;
import app.drive.service.TripService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping(value="/api/trips")
public class TripController {

    @NonNull
    private final TripService tripService;

    @GetMapping(value = "/get-trip/{passengerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TripDto>> getTripHistory(@PathVariable Long passengerId) {
        log.info("Received request from passenger: {} for history of their trips", passengerId);
        var tripHistories = tripService.getTrips(passengerId);

        if (tripHistories.isEmpty()) {
            log.warn("Trip history is empty. ");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity<>(tripHistories, HttpStatus.OK);
    }
}
