package app.drive.repository;

import app.drive.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    @Query(value = "SELECT * from VEHICLE_ENTITY where is_available is TRUE", nativeQuery = true)
    List<VehicleEntity> findAvailableVehicles();

    @Modifying
    @Transactional
    @Query(value = "UPDATE VEHICLE_ENTITY SET latitude = :latitude, longitude = :longitude WHERE id = :id", nativeQuery = true)
    void updateVehicleLocation(@Param("id") Long id, @Param("latitude") double latitude, @Param("longitude") double longitude);
}
