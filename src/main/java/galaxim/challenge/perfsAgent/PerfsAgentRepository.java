package galaxim.challenge.perfsAgent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfsAgentRepository extends JpaRepository<PerfsAgent, Long> {
}
