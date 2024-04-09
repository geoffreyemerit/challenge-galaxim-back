package galaxim.challenge.perfsMandatary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfsMandataryRepository extends JpaRepository<PerfsMandatary, Long> {
}
