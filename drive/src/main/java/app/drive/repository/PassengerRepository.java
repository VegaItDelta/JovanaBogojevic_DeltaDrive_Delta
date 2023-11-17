package app.drive.repository;

import app.drive.model.entity.PassengerEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {

    PassengerEntity findByEmailAndPassword(String email, String password);
}
