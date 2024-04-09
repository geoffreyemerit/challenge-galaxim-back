package galaxim.challenge.performance;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

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

    public Performance add(Performance performance, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            performance.setAddDate(performance.getAddDate());
            performance.setCaHtAct(performance.getCaHtAct());
            performance.setCaHtSsp(performance.getCaHtSsp());
            performance.setMandate(performance.getMandate());

            return performanceRepository.save(performance);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
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
