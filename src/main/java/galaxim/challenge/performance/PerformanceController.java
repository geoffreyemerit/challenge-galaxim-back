package galaxim.challenge.performance;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/all")
    public List<Performance> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return performanceService.getAll(role);
    }

    @GetMapping("/{id}")
    public Performance getById(@PathVariable Long id) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return performanceService.getById(id, role);
    }


    @PostMapping("/add")
    public Performance add(@RequestBody Performance performance){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return performanceService.add(performance, role);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        performanceService.delete(id, role);
    }
}
