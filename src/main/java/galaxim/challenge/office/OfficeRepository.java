package galaxim.challenge.office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface OfficeRepository extends JpaRepository<Office, Long> {

    Optional<Office> findByNameOffice(String lowerCase);

    @Query("SELECT o FROM Office o WHERE o.caHtOfficeSsp > 0 ORDER BY o.caHtOfficeSsp DESC LIMIT 20")
    List<Office> findTop20OfficeByCaHtOfficeSsp();
}

