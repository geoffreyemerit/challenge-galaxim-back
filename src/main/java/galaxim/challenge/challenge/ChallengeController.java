package galaxim.challenge.challenge;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/all")
    public List<Challenge> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return challengeService.getAll(role);
    }

    @GetMapping("{id}")
    public Challenge getById(@PathVariable Long id) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return challengeService.getById(id, role);
    }
    @PostMapping("/add")
    public Challenge add(@RequestBody Challenge challenge){

        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return challengeService.add(challenge, role);
    }

    @PutMapping("/update")
    public ResponseEntity<Challenge> update(@RequestBody Challenge updatedChallenge) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        Challenge updated = challengeService.update(updatedChallenge, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id){
       String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        challengeService.delete(id, role);
    }
}
