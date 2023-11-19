package app.drive.repository;

import app.drive.model.dto.PassengerRequestDto;
import app.drive.model.entity.PassengerEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {

    PassengerEntity findByEmailAndPassword(String email, String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE passenger_entity SET current_latitude = :#{#updateDTO.currentLocation.latitude}, " +
            "current_longitude = :#{#updateDTO.currentLocation.longitude}, desired_latitude = :#{#updateDTO.targetLocation.latitude}, " +
            "desired_longitude = :#{#updateDTO.targetLocation.longitude} WHERE id = :#{#updateDTO.passengerId}", nativeQuery = true)
    void updatePassengerLocation(@Param("updateDTO") PassengerRequestDto updateDTO);
}
