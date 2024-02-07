package galaxim.challenge.office;

import galaxim.challenge.user.User;
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

    public Office getById(Long id, String role){
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));

        if (role.equals("[ROLE_ADMIN]")) {
            return office;
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Office add(Office office, String username, String role) {

        if (role.equals("[ROLE_ADMIN]")) {
            if (office.getNameOffice() != null && officeRepository.findByNameOffice(office.getNameOffice().toLowerCase()).isPresent()) {
                throw new IllegalArgumentException("Office already exists.");
            }
            // Mettre à jour les propriétés du bureau existant
            office.setBrand(office.getBrand());
            office.setNameOffice(office.getNameOffice());
            office.setCity(office.getCity());
            office.setCaHtOfficeSsp(office.getCaHtOfficeSsp());

            // Enregistrer les modifications du bureau existant
            return officeRepository.save(office);

        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
        }

    public Office update(Office updatedOffice, String username, String role) {

        Office currentOffice = officeRepository.findById(updatedOffice.getId())
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + updatedOffice.getId()));

        if (role.equals("[ROLE_ADMIN]")) {

            // Vérifier si le nameOffice existe déjà pour un autre bureau
            String newNameOffice = updatedOffice.getNameOffice();
            if (newNameOffice != null && !newNameOffice.equalsIgnoreCase(currentOffice.getNameOffice())) {
                // Vérifier la présence du bureau par nom
                if (officeRepository.findByNameOffice(newNameOffice.toLowerCase()).isPresent()) {
                    throw new IllegalArgumentException("Office with the name already exists.");
                }
            }

            currentOffice.setBrand(updatedOffice.getBrand());
            currentOffice.setNameOffice(updatedOffice.getNameOffice());
            currentOffice.setCity(updatedOffice.getCity());
            currentOffice.setCaHtOfficeSsp(updatedOffice.getCaHtOfficeSsp());
            currentOffice.setUserList(updatedOffice.getUserList());

            return officeRepository.save(currentOffice);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }

    public void delete(Long id, String username, String role) {

        // Recherche de l'agence par id
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found with id: " + id));

        // Vérification si l'utilisateur connecté a le droit de supprimer l'utilisateur
        if (role.equals("[ROLE_ADMIN]")) {
            // Suppression de l'agence par ID
            officeRepository.deleteById(id);
        } else {
            // Gestion du cas où l'utilisateur connecté n'a pas le droit de supprimer l'utilisateur
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }
    public List<Office> findTop20OfficeByCaHtOfficeSsp() {
        return officeRepository.findTop20OfficeByCaHtOfficeSsp();
    }
}
