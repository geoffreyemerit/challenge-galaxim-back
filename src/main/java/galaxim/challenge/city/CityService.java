package galaxim.challenge.city;

import galaxim.challenge.office.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final OfficeRepository officeRepository;
    public List<City> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return cityRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public City getById(Long id, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return cityRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public City add(City city, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            if (city.getCity() != null && cityRepository.findByCity(city.getCity().toLowerCase().trim()).isPresent()) {
                throw new IllegalArgumentException("City already exists.");
            }

            if (city.getCity() != null) {
                city.setCity(city.getCity().trim());
            }

            city.setZip(city.getZip());

            return cityRepository.save(city);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }


    public City update(City updatedCity, String role) {
        City currentCity = cityRepository.findById(updatedCity.getId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + updatedCity.getId()));

        if(role.equals("[ROLE_ADMIN]")){
            String newNameCity = updatedCity.getCity();
            if(newNameCity != null && !newNameCity.equalsIgnoreCase(currentCity.getCity())){
                if(cityRepository.findByCity(newNameCity.toLowerCase().trim()).isPresent()){
                    throw new IllegalArgumentException("City with the name already exists.");
                }
            }

            currentCity.setCity(updatedCity.getCity().trim());
            currentCity.setZip(updatedCity.getZip());

            return cityRepository.save(currentCity);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }

    public void delete(Long id, String role) {
        City cityToDelete = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));

        if (role.equals("[ROLE_ADMIN]")) {
            if (!officeRepository.existsByCity(cityToDelete)) {
                cityRepository.delete(cityToDelete);
            } else {
                throw new RuntimeException("Cannot delete city because offices are associated with it");
            }
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }
}
