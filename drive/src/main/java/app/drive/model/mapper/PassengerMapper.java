package app.drive.model.mapper;

import app.drive.model.dto.PassengerDto;
import app.drive.model.entity.PassengerEntity;

public class PassengerMapper {

    private PassengerMapper() {

    }

    public static PassengerEntity toPassengerEntity(PassengerDto passengerDto) {
        return PassengerEntity
                .builder()
                .id(passengerDto.getId())
                .firstName(passengerDto.getFirstName())
                .lastName(passengerDto.getLastName())
                .password(passengerDto.getPassword())
                .birthday(passengerDto.getBirthday())
                .email(passengerDto.getEmail())
                .build();
    }

    public static PassengerDto toPassengerDto(PassengerEntity passengerEntity) {
        return PassengerDto
                .builder()
                .id(passengerEntity.getId())
                .firstName(passengerEntity.getFirstName())
                .lastName(passengerEntity.getLastName())
                .password(passengerEntity.getPassword())
                .birthday(passengerEntity.getBirthday())
                .email(passengerEntity.getEmail())
                .build();
    }
}
