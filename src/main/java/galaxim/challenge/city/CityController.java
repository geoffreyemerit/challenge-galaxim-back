package galaxim.challenge.city;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/all")
    public List<City> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return cityService.getAll(role);
    }

    @GetMapping("/{id}")
    public City getById(@PathVariable Long id){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return cityService.getById(id, role);
    }

    @PostMapping("/add")
    public City add(@RequestBody City city){

        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        return cityService.add(city, role);
    }

    @PutMapping("/update")
    public ResponseEntity<City> update(@RequestBody City updatedCity){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        City updated = cityService.update(updatedCity, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        cityService.delete(id,role);
    }

}
