package galaxim.challenge.office;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    public List<Office> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return officeRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Office getById(Long id, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return officeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Office add(Office office, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            if (office.getNameOffice() != null && officeRepository.findByNameOffice(office.getNameOffice().toLowerCase()).isPresent()) {
                throw new IllegalArgumentException("Office already exists.");
            }

            office.setNameOffice(office.getNameOffice());

            return officeRepository.save(office);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Office update(Office updatedOffice, String role) {
        Office currentOffice = officeRepository.findById(updatedOffice.getId())
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + updatedOffice.getId()));

        if (role.equals("[ROLE_ADMIN]")) {
            String newNameOffice = updatedOffice.getNameOffice();

            if(newNameOffice != null && !newNameOffice.equalsIgnoreCase(currentOffice.getNameOffice())){
                if(officeRepository.findByNameOffice(newNameOffice.toLowerCase()).isPresent()){
                    throw new IllegalArgumentException("Office with the name already exists.");
                }
            }
            currentOffice.setNameOffice(updatedOffice.getNameOffice());
            return officeRepository.save(currentOffice);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }

    public void delete(Long id, String role) {
        Office officeToDelete = officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));

        if(role.equals("[ROLE_ADMIN]")){
            officeRepository.deleteById(officeToDelete.getId());
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }
}
