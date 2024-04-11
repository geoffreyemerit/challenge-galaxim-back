package galaxim.challenge.user;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getById(id, role);
    }

    @GetMapping("/team/{job}")
    public List<User> getByJob(@PathVariable String job) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getByJob(job, role);
    }

    @GetMapping("/team/{job}/brand/{brand}")
    public List<User> getByJobAndBrand(@PathVariable String job, @PathVariable String brand) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return userService.getByJobAndBrand(job, brand, role);
    }

    @GetMapping("/team/{job}/performance/{performance}/top20")
    public List<User> getByJobAndPerformance(@PathVariable String job, @PathVariable String performance) {
        return userService.getByJobAndPerformance(job, performance);
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

}
