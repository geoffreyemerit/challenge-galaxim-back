package galaxim.challenge.user;

import galaxim.challenge.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByEmail(String email);
   boolean existsByJob(Job job);
   boolean existsByFirstnameAndLastname(String lowerCaseFirstName, String lowerCaseLastName);

    Optional<Object> findByFirstnameAndLastname(String newFirstname, String newLastname);

    @Query("SELECT u FROM User u JOIN u.office o JOIN o.brand b WHERE u.job.nameJob = :job AND b.nameBrand = :brand")
    List<User> findByNameJobAndNameBrand(@Param("job") String job, @Param("brand") String brand);

    @Query("SELECT u FROM User u " +
            "JOIN Performance p ON p.user.id = u.id " +
            "JOIN Job j ON j.id = u.job.id " +
            "WHERE j.nameJob = :job " +
            "AND (:performance = 'caHtSsp' AND p.caHtSsp IS NOT NULL OR " +
            "     :performance = 'mandate' AND p.mandate IS NOT NULL OR " +
            "     :performance = 'caHtAct' AND p.caHtAct IS NOT NULL) " +
            "AND p.addDate = (SELECT MAX(p2.addDate) FROM Performance p2 WHERE p2.user.id = u.id) " +
            "ORDER BY CASE " +
            "    WHEN :performance = 'caHtSsp' THEN p.caHtSsp " +
            "    WHEN :performance = 'mandate' THEN p.mandate " +
            "    WHEN :performance = 'caHtAct' THEN p.caHtAct " +
            "END DESC")
    List<User> getByJobAndPerformance(@Param("job") String job, @Param("performance") String performance);


}
