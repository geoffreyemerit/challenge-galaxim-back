package galaxim.challenge.office;

import galaxim.challenge.user.User;
import galaxim.challenge.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficeUserService {

    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;
    public Office bindUserWithOffice(Long userId, Long officeId, String role) {
        User userFound = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with id: " + userId));

        Office officeFound = officeRepository.findById(officeId).orElseThrow(() -> new RuntimeException("office not found with id: " + officeId));

        // Vérifier si l'utilisateur est déjà associé à cet office
        if (officeFound.getUserList().contains(userFound)) {
            throw new RuntimeException("user with id " + userId + " is already associated with office id " + officeId);
        }

        if (role.equals("[ROLE_ADMIN]")) {
            officeFound.getUserList().add(userFound);

            return officeRepository.save(officeFound);
        } else {
            // Gestion du cas où l'utilisateur connecté n'a pas le droit de supprimer l'utilisateur
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }

    public Office unbindUserFromOffice(Long userId, Long officeId, String role) {
        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found with id: " + userId));

        Office officeFound = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("office not found with id: " + officeId));



        if (role.equals("[ROLE_ADMIN]")) {
            // Retirer l'utilisateur de la liste des utilisateurs de l'office
            officeFound.getUserList().remove(userFound);
            return officeRepository.save(officeFound);
        } else {
            // Gestion du cas où l'utilisateur connecté n'a pas le droit de supprimer l'utilisateur
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }
}
