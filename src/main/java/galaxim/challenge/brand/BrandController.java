package galaxim.challenge.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/all")
    public List<Brand> getAll() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return brandService.getAll(role);
    }

    @GetMapping("{id}")
    public Brand getById(@PathVariable Long id){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return brandService.getById(id, role);
    }

    @PostMapping("/add")
    public Brand add(@RequestBody Brand brand){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return brandService.add(brand, role);
    }

    @PutMapping("/update")
    public ResponseEntity<Brand> update(@RequestBody Brand updatedBrand){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        Brand updated = brandService.update(updatedBrand, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id){
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        brandService.delete(id, role);
    }
}
