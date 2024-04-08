package galaxim.challenge.job;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/all")
    public List<Job> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return jobService.getAll(role);
    }

    @GetMapping("{id}")
    public Job getById(@PathVariable Long id) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return jobService.getById(id, role);
    }

    @PostMapping("/add")
    public Job add(@RequestBody Job job){

        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return jobService.add(job, role);
    }

    @PutMapping("/update")
    public ResponseEntity<Job> update(@RequestBody Job updatedJob) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        Job updated = jobService.update(updatedJob, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        jobService.delete(id, role);
    }
}
