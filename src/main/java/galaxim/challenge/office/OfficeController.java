package galaxim.challenge.office;

import galaxim.challenge.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/all")
    public List<Office> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return officeService.getAll(role);
    }

    @GetMapping("{id}")
    public Office getById(@PathVariable Long id) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return officeService.getById(id, role);
    }

    @PostMapping("/add")
    public Office add(@RequestBody Office office){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return officeService.add(office, username, role);
    }

    @PutMapping("/update")
    public ResponseEntity<Office> update(@RequestBody Office updatedOffice) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        Office updated = officeService.update(updatedOffice, username, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        officeService.delete(id, username, role);
    }

    @GetMapping("/top20/caHtOfficeSsp")
    public List<Office> findTop20OfficeByCaHtOfficeSsp() {
        return officeService.findTop20OfficeByCaHtOfficeSsp();
    }

}
