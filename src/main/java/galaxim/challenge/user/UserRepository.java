package galaxim.challenge.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByJob(String job);
   boolean existsByFirstnameAndLastname(String lowerCaseFirstName, String lowerCaseLastName);
   Optional<User> findByFirstnameAndLastname(String newFirstname, String newLastname);

   List<User> findByJobAndOffice_Brand(String job, String brand);

    @Query("SELECT u FROM User u WHERE u.job='AGENCES' AND u.caHtAct > 0 ORDER BY u.caHtAct DESC LIMIT 20")
    List<User> findTop20AgentByCaHtAct();

   @Query("SELECT u FROM User u WHERE u.job='AGENCES' AND u.caHtSsp > 0 ORDER BY u.caHtSsp DESC LIMIT 20")
    List<User> findTop20AgentByCaHtSsp();
    @Query("SELECT u FROM User u WHERE u.job='AGENCES' AND u.salesSsp > 0 ORDER BY u.salesSsp DESC LIMIT 20")
    List<User> findTop20AgentBySalesSsp();
    @Query("SELECT u FROM User u WHERE u.job='AGENCES' AND u.mandates > 0 ORDER BY u.mandates DESC LIMIT 20")
    List<User> findTop20AgentByMandates();
    @Query("SELECT u FROM User u WHERE u.job='MANDATAIRES' AND u.caHtAct > 0 ORDER BY u.caHtAct DESC LIMIT 20")
    List<User> findTop20MandataireByCaHtAct();
    @Query("SELECT u FROM User u WHERE u.job='MANDATAIRES' AND u.caHtSsp > 0 ORDER BY u.caHtSsp DESC LIMIT 20")
    List<User> findTop20MandataireByCaHtSsp();
    @Query("SELECT u FROM User u WHERE u.job='MANDATAIRES' AND u.mandates > 0 ORDER BY u.mandates DESC LIMIT 20")
    List<User> findTop20MandataireByMandates();
    @Query("SELECT u FROM User u WHERE  u.job='MANDATAIRES' AND u.bestDev > 0 ORDER BY u.bestDev DESC LIMIT 20")
    List<User> findTop20MandataireByBestDev();
    @Query("SELECT u FROM User u WHERE  u.job='MANDATAIRES' AND u.caHtNetworkTeamSsp > 0 ORDER BY u.caHtNetworkTeamSsp DESC LIMIT 20")
    List<User> findTop20MandataireByCaHtNetworkTeamSsp();


    @Query("SELECT u FROM User u WHERE u.office.brand='WEELODGE' AND u.caAllActions > 0 ORDER BY u.caAllActions DESC LIMIT 10")
    List<User> findTop10ByCaAllActions();
}