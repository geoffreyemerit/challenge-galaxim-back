package galaxim.challenge.user;

import galaxim.challenge.job.Job;
import galaxim.challenge.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public List<User> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return userRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public User getById(Long id, String role){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (role.equals("[ROLE_ADMIN]")) {
            return user;
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public List<User> getByJob(String jobName, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            Optional<Job> jobOptional = jobRepository.findByNameJob(jobName);
            if (jobOptional.isPresent()) {
                Job jobEntity = jobOptional.get();
                return jobEntity.getUserList();
            } else {
                throw new RuntimeException("Job not found with name: " + jobName);
            }
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }

    public List<User> getByJobAndBrand(String job, String brand, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            if(job != null && brand != null) {
                return userRepository.findByNameJobAndNameBrand(job, brand);
            } else {
                throw new RuntimeException("Brand or Job not found");
            }
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }

    public List<User> getByJobAndPerformance(String job, String performance) {
        if(job != null && performance != null ) {
            return userRepository.getByJobAndPerformance(job, performance);
        } else {
            throw new RuntimeException("Performance or Job not found");
        }
    }

    public User add(User user, String username, String role) {

        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        if (username.equals(currentUser.getEmail()) || role.equals("[ROLE_ADMIN]")) {
            if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists.");
            }

            String lowerCaseFirstName = user.getFirstname().toLowerCase().trim();
            String lowerCaseLastName = user.getLastname().toLowerCase().trim();
            if (userRepository.existsByFirstnameAndLastname(lowerCaseFirstName, lowerCaseLastName)) {
                throw new IllegalArgumentException("User with the same name and surname already exists.");
            }

            user.setFirstname(user.getFirstname().trim());
            user.setLastname(user.getLastname().trim());
            user.setPhoto(user.getPhoto().trim());
            user.setIsActive(user.getIsActive());
            user.setJob(user.getJob());
            user.setOffice(user.getOffice());

            return userRepository.save(user);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public User update(User updatedUser, String username, String role) {
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        if (username.equals(currentUser.getEmail()) || role.equals("[ROLE_ADMIN]")) {
            User existingUser = userRepository.findById(updatedUser.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + updatedUser.getId()));

            String newEmail = updatedUser.getEmail();
            if (newEmail != null && !newEmail.equalsIgnoreCase(existingUser.getEmail())) {
                if (userRepository.findByEmail(newEmail).isPresent()) {
                    throw new IllegalArgumentException("User with the email already exists.");
                }
            }

            String newFirstname = updatedUser.getFirstname();
            String newLastname = updatedUser.getLastname();
            if (newFirstname != null && newLastname != null &&
                    (!newFirstname.equalsIgnoreCase(existingUser.getFirstname()) || !newLastname.equalsIgnoreCase(existingUser.getLastname()))) {
                if (userRepository.findByFirstnameAndLastname(newFirstname, newLastname).isPresent()) {
                    throw new IllegalArgumentException("User with the firstname and lastname already exists.");
                }
            }

            existingUser.setFirstname(updatedUser.getFirstname().trim());
            existingUser.setLastname(updatedUser.getLastname().trim());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoto(updatedUser.getPhoto());
            existingUser.setIsActive(updatedUser.getIsActive());
            existingUser.setJob(updatedUser.getJob());
            existingUser.setOffice(updatedUser.getOffice());

            return userRepository.save(existingUser);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }

    public void delete(Long id, String username, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (user != null && username.equals(user.getEmail()) || role.equals("[ROLE_ADMIN]")) {
            userRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }

}
