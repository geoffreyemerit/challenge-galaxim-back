package galaxim.challenge.office;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bind")
@RequiredArgsConstructor
public class OfficeUserController {

    private final OfficeUserService officeUserService;

    @GetMapping("/userId={userId}/officeId={officeId}")
    public Office bindUserWithOffice(@PathVariable("userId") Long userId, @PathVariable("officeId") Long officeId){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return officeUserService.bindUserWithOffice(userId, officeId, role);
    }

    @DeleteMapping("/userId={userId}/officeId={officeId}")
    public Office unbindUserFromOffice(@PathVariable("userId") Long userId, @PathVariable("officeId") Long officeId) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return officeUserService.unbindUserFromOffice(userId, officeId, role);
    }
}
