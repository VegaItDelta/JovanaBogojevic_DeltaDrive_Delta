package app.drive.repository;

import app.drive.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long> {
}
