package galaxim.challenge.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return userRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public List<User> getUsersByJobAndBrand(String job, String brand, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return userRepository.findByJobAndOffice_Brand(job, brand);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public List<User> getByJob(String job, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return userRepository.findByJob(job);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public User getByEmail(String email, String username, String role) {
        if (username.equals(email) || role.equals("[ROLE_ADMIN]")) {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User with email " + email +" not found"));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public User getById(Long id, String username, String role){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (username.equals(user.getEmail()) || role.equals("[ROLE_ADMIN]")) {
            return user;
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public User add(User user, String username, String role) {

        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        if (username.equals(currentUser.getEmail()) || role.equals("[ROLE_ADMIN]")) {
            if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists.");
            }

            // Vérifier si un utilisateur avec le même nom et prénom existe déjà
            String lowerCaseFirstName = user.getFirstname().toLowerCase();
            String lowerCaseLastName = user.getLastname().toLowerCase();
            if (userRepository.existsByFirstnameAndLastname(lowerCaseFirstName, lowerCaseLastName)) {
                throw new IllegalArgumentException("User with the same name and surname already exists.");
            }

            // Définir les propriétés communes
            user.setFirstname(user.getFirstname());
            user.setLastname(user.getLastname());
            user.setPhoto(user.getPhoto());
            user.setJob(user.getJob());
            user.setCaHtAct(user.getCaHtAct());
            user.setCaHtSsp(user.getCaHtSsp());
            user.setMandates(user.getMandates());
            user.setSalesSsp(user.getSalesSsp());
            user.setBestDev(user.getBestDev());
            user.setCaHtNetworkTeamSsp(user.getCaHtNetworkTeamSsp());
            user.setCaAllActions(user.getCaAllActions());

            return userRepository.save(user);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public User update(User updatedUser, String username, String role) {

        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        if (role.equals("[ROLE_ADMIN]")) {
            // Only ROLE_ADMIN is allowed to update users
            User existingUser = userRepository.findById(updatedUser.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + updatedUser.getId()));

            // Vérifier si l'email existe déjà pour un autre utilisateur
            String newEmail = updatedUser.getEmail();
            if (newEmail != null && !newEmail.equalsIgnoreCase(existingUser.getEmail())) {
                // Vérifier la présence de l'utilisateur par email
                if (userRepository.findByEmail(newEmail).isPresent()) {
                    throw new IllegalArgumentException("User with the email already exists.");
                }
            }

            // Vérifier si le nom et prénom existent déjà pour un autre utilisateur
            String newFirstname = updatedUser.getFirstname();
            String newLastname = updatedUser.getLastname();
            if (newFirstname != null && newLastname != null &&
                    (!newFirstname.equalsIgnoreCase(existingUser.getFirstname()) || !newLastname.equalsIgnoreCase(existingUser.getLastname()))) {
                // Vérifier la présence de l'utilisateur par nom et prénom
                if (userRepository.findByFirstnameAndLastname(newFirstname, newLastname).isPresent()) {
                    throw new IllegalArgumentException("User with the firstname and lastname already exists.");
                }
            }


            // Copy the updated fields
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoto(updatedUser.getPhoto());
            existingUser.setJob(updatedUser.getJob());
            existingUser.setCaHtAct(updatedUser.getCaHtAct());
            existingUser.setCaHtSsp(updatedUser.getCaHtSsp());
            existingUser.setSalesSsp(updatedUser.getSalesSsp());
            existingUser.setMandates(updatedUser.getMandates());
            existingUser.setBestDev(updatedUser.getBestDev());
            existingUser.setCaHtNetworkTeamSsp(updatedUser.getCaHtNetworkTeamSsp());
            existingUser.setCaAllActions(updatedUser.getCaAllActions());

            return userRepository.save(existingUser);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }

    public void delete(Long id, String username, String role) {
        // Recherche de l'utilisateur par id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Vérification si l'utilisateur connecté a le droit de supprimer l'utilisateur
        if (user != null && username.equals(user.getEmail()) || role.equals("[ROLE_ADMIN]")) {
            // Suppression de l'utilisateur par ID
            userRepository.deleteById(id);
        } else {
            // Gestion du cas où l'utilisateur connecté n'a pas le droit de supprimer l'utilisateur
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }

    public List<User> findTop20AgentByOrderByCaHtActDesc() { return userRepository.findTop20AgentByCaHtAct(); }
    public List<User> findTop20AgentByCaHtSsp() { return userRepository.findTop20AgentByCaHtSsp(); }
    public List<User> findTop20AgentBySalesSsp() { return userRepository.findTop20AgentBySalesSsp(); }
    public List<User> findTop20AgentByMandates() {
        return userRepository.findTop20AgentByMandates();
    }
    public List<User> findTop20MandataireByCaHtAct() {
        return userRepository.findTop20MandataireByCaHtAct();
    }
    public List<User> findTop20MandataireByCaHtSsp() {
        return userRepository.findTop20MandataireByCaHtSsp();
    }
    public List<User> findTop20MandataireByMandates() {
        return userRepository.findTop20MandataireByMandates();
    }
    public List<User> findTop20MandataireByBestDev() {
        return userRepository.findTop20MandataireByBestDev();
    }
    public List<User> findTop20MandataireByCaHtNetworkTeamSsp() {
        return userRepository.findTop20MandataireByCaHtNetworkTeamSsp();
    }

    public List<User> findTop10ByCaAllActions() {
        return userRepository.findTop10ByCaAllActions();
    }


}