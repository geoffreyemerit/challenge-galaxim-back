package galaxim.challenge.perfsAgent;

import galaxim.challenge.performance.Performance;
import galaxim.challenge.performance.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/perfsAgent")
@RequiredArgsConstructor
public class PerfsAgentController {

    private final PerfsAgentService perfsAgentService;
    private final PerformanceService performanceService;

    @PostMapping("/add")
    public PerfsAgent add(@RequestBody PerfsAgent perfsAgent) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Performance savedPerformance = performanceService.add(perfsAgent.getPerformance(), role);

        return perfsAgentService.add(savedPerformance, perfsAgent, role);
    }
}
