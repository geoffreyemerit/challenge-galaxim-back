package galaxim.challenge.perfsMandatary;

import galaxim.challenge.performance.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfsMandataryService {

    private final PerfsMandataryRepository perfsMandataryRepository;

    public PerfsMandatary add(Performance savedPerformance, PerfsMandatary perfsMandatary, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            perfsMandatary.setRecruiters(perfsMandatary.getRecruiters());
            perfsMandatary.setCaHtSspNetwork(perfsMandatary.getCaHtSspNetwork());
            perfsMandatary.setPerformance(savedPerformance);

            return perfsMandataryRepository.save(perfsMandatary);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }


}
