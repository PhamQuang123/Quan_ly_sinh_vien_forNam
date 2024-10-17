package example.qlhs.repository;

import example.qlhs.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository  extends JpaRepository<Personal, Long> {
}
