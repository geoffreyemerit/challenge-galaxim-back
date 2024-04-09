package galaxim.challenge.perfsAgent;

import galaxim.challenge.performance.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfsAgentService {

    private final PerfsAgentRepository perfsAgentRepository;

    public PerfsAgent add(Performance savedPerformance, PerfsAgent perfsAgent, String role) {

        if (role.equals("[ROLE_ADMIN]")) {
            perfsAgent.setSalesSsp(perfsAgent.getSalesSsp());
            perfsAgent.setPerformance(savedPerformance);
            return perfsAgentRepository.save(perfsAgent);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }



}
