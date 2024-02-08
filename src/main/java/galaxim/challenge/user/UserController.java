package galaxim.challenge.user;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<User> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getAll(role);
    }

    @GetMapping("/team/{job}")
    public List<User> getByJob(@PathVariable String job) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getByJob(job, role);
    }

    @GetMapping("/job/{job}/brand/{brand}")
    public List<User> getUsersByJobAndBrand(@PathVariable String job, @PathVariable String brand) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getUsersByJobAndBrand(job, brand, role);
    }

    @GetMapping("/email/{email}")
    public User getByEmail(@PathVariable String email) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getByEmail(email, username, role);
    }

    @GetMapping("{id}")
    public User getById(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getById(id, username, role);
    }

    @PostMapping("/add")
    public User add(@RequestBody User user){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return userService.add(user, username, role);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User updatedUser) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

    if (updatedUser.getId() == null) {
        throw new IllegalArgumentException("User ID must not be null");
    }

        User updated = userService.update(updatedUser, username, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        userService.delete(id, username, role);
    }

    @GetMapping("/top20/agents/caHtAct")
    public List<User> findTop20AgentByOrderByCaHtActDesc() {
        return userService.findTop20AgentByOrderByCaHtActDesc();
    }

    @GetMapping("/top20/agents/caHtSsp")
    public List<User> findTop20AgentByCaHtSsp() {
        return userService.findTop20AgentByCaHtSsp();
    }

    @GetMapping("/top20/agents/salesSsp")
    public List<User> findTop20AgentBySalesSsp() {
        return userService.findTop20AgentBySalesSsp();
    }

    @GetMapping("/top20/agents/mandates")
    public List<User> findTop20AgentByMandates() {
        return userService.findTop20AgentByMandates();
    }

    @GetMapping("/top20/mandataires/caHtAct")
    public List<User> findTop20MandataireByCaHtAct() {
        return userService.findTop20MandataireByCaHtAct();
    }

    @GetMapping("/top20/mandataires/caHtSsp")
    public List<User> findTop20MandataireByCaHtSsp() {
        return userService.findTop20MandataireByCaHtSsp();
    }

    @GetMapping("/top20/mandataires/mandates")
    public List<User> findTop20MandataireByMandates() {
        return userService.findTop20MandataireByMandates();
    }

    @GetMapping("/top20/mandataires/bestDev")
    public List<User> findTop20MandataireByBestDev() {
        return userService.findTop20MandataireByBestDev();
    }

    @GetMapping("/top20/mandataires/caHtNetworkTeamSsp")
    public List<User> findTop20MandataireByCaHtNetworkTeamSsp() {
        return userService.findTop20MandataireByCaHtNetworkTeamSsp();
    }

    @GetMapping("/top10/caAllActions")
    public List<User> findTop10ByCaAllActions() {
        return userService.findTop10ByCaAllActions();
    }
}
