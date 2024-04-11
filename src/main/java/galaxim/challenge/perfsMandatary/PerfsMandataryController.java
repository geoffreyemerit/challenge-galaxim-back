package galaxim.challenge.perfsMandatary;

import galaxim.challenge.performance.Performance;
import galaxim.challenge.performance.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/perfsMandatary")
@RequiredArgsConstructor
public class PerfsMandataryController {

    private final PerfsMandataryService perfsMandataryService;
    private final PerformanceService performanceService;

    /*
    @GetMapping("/{idPerformance}")
    public PerfsMandatary getByIdPerformance(@PathVariable Long idPerformance){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return perfsMandataryService.getByIdPerformance(idPerformance, role);
    }
    */

    @PostMapping("/add")
    public PerfsMandatary add(@RequestBody PerfsMandatary perfsMandatary) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Performance savedPerformance = performanceService.add(perfsMandatary.getPerformance(), role);

        return perfsMandataryService.add(savedPerformance, perfsMandatary, role);
    }
}
