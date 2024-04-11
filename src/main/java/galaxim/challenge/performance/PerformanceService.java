package galaxim.challenge.performance;

import galaxim.challenge.perfsAgent.PerfsAgentService;
import galaxim.challenge.perfsMandatary.PerfsMandataryService;
import galaxim.challenge.user.User;
import galaxim.challenge.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    private final PerfsAgentService perfsAgentService;
    private final UserService userService;

    public List<Performance> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return performanceRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Performance getById(Long id, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return performanceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Performance not found with id: " + id));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }
    public Performance add(Performance performance, String role){
        if (role.equals("[ROLE_ADMIN]")) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
            performance.setAddDate(currentTimestamp);

            Long userId = performance.getUser().getId();
            User user = userService.getById(userId, role);

            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " does not exist");
            }

            performance.setUser(user);

            Performance savedPerformance = performanceRepository.save(performance);

            if (performance.getPerfsAgent() != null) {
                perfsAgentService.add(savedPerformance, performance.getPerfsAgent(), role);
            }

            return savedPerformance;
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }

    public void delete(Long id, String role) {
        Performance performanceToDelete = performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance not found with id: " + id));

        if (role.equals("[ROLE_ADMIN]")) {
            performanceRepository.deleteById(performanceToDelete.getId());
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }
}
