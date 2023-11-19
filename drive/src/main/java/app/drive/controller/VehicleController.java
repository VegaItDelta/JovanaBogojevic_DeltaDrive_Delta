package app.drive.controller;

import app.drive.model.dto.PassengerRequestDto;
import app.drive.model.dto.VehicleDto;

import app.drive.model.mapper.VehicleMapper;
import app.drive.service.VehicleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/vehicles")
public class VehicleController {

    @NonNull
    private final VehicleService vehicleService;

    @PostMapping(value = "/find-nearest-vehicles")
    public ResponseEntity<List<VehicleDto>> getNearestVehicles(@RequestBody PassengerRequestDto passengerRequestDto) {
        log.info("Received request to find nearest available vehicles. ");
        var nearestVehicles = vehicleService.findNearestVehicles(passengerRequestDto);

        if(nearestVehicles.isEmpty()) {
            log.warn("There are no available vehicles. ");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(VehicleMapper.toVehicleDtoList(nearestVehicles), HttpStatus.FOUND);
    }

    @PostMapping("/book_vehicle/{passengerId}/{vehicleId}")
    public ResponseEntity<String> bookVehicle(@PathVariable Long passengerId, @PathVariable Long vehicleId) {
        log.info("Received request to book vehicle with id:{}, from passenger:{}", vehicleId, passengerId);
        var isBooked = vehicleService.handleBooking(vehicleId, passengerId);

        if(!isBooked) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Driver rejected the booking request");
        }

        return ResponseEntity.ok("Vehicle booked successfully");
    }
}
