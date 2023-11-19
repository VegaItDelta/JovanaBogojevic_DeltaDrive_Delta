package app.drive.repository;

import app.drive.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    @Query(value = "SELECT * from VEHICLE_ENTITY where is_available is TRUE", nativeQuery = true)
    List<VehicleEntity> findAvailableVehicles();
}
