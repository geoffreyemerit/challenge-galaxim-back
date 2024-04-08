package galaxim.challenge.user;

import lombok.RequiredArgsConstructor;

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

}
