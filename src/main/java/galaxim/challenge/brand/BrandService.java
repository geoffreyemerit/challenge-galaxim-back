package galaxim.challenge.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    public List<Brand> getAll(String role) {
        if(role.equals("[ROLE_ADMIN]")){
            return brandRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Brand getById(Long id, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return brandRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Brand add(Brand brand, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            if (brand.getNameBrand() != null && brandRepository.findByNameBrand(brand.getNameBrand().toLowerCase()).isPresent()) {
                throw new IllegalArgumentException("Brand already exists.");
            }

            brand.setNameBrand(brand.getNameBrand());

            return brandRepository.save(brand);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Brand update(Brand updatedBrand, String role) {
        Brand currentBrand = brandRepository.findById(updatedBrand.getId())
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + updatedBrand.getId()));

        if (role.equals("[ROLE_ADMIN]")) {
            String newNameBrand = updatedBrand.getNameBrand();
            if (newNameBrand != null && !newNameBrand.equalsIgnoreCase(currentBrand.getNameBrand())) {
                if (brandRepository.findByNameBrand(newNameBrand.toLowerCase()).isPresent()) {
                    throw new IllegalArgumentException("Brand with the name already exists.");
                }
            }
            currentBrand.setNameBrand(updatedBrand.getNameBrand());
            return brandRepository.save(currentBrand);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }


    public void delete(Long id, String role) {
        Brand brandToDelete = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));

        if (role.equals("[ROLE_ADMIN]")) {
            brandRepository.deleteById(brandToDelete.getId());
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }

    
}
