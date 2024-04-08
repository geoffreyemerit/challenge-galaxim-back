package galaxim.challenge.challenge;

import galaxim.challenge.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<galaxim.challenge.challenge.Challenge, Long> {

    boolean existsByJob(Job job);

    Optional<Object> findByNameChallenge(String lowerCase);
}
