package backend.datn.repositories;

import backend.datn.entities.Collar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollarRepository extends JpaRepository<Collar, Integer> {
}