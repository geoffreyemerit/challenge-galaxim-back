package galaxim.challenge.challenge;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public List<Challenge> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return challengeRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Challenge getById(Long id, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return challengeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Challenge not found with id: " + id));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }
    public Challenge add(Challenge challenge, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            if (challenge.getNameChallenge() != null && challengeRepository.findByNameChallenge(challenge.getNameChallenge().toLowerCase()).isPresent()) {
                throw new IllegalArgumentException("Challenge already exists.");
            }

            challenge.setNameChallenge(challenge.getNameChallenge());

            return challengeRepository.save(challenge);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }


    public Challenge update(Challenge updatedChallenge, String role) {
        Challenge currentChallenge = challengeRepository.findById(updatedChallenge.getId())
                .orElseThrow(() -> new RuntimeException("Challenge not found with id: " + updatedChallenge.getId()));

        if (role.equals("[ROLE_ADMIN]")) {
            String newNameChallenge = updatedChallenge.getNameChallenge();
            if(newNameChallenge  != null && !newNameChallenge.equalsIgnoreCase(currentChallenge.getNameChallenge())){
                if (challengeRepository.findByNameChallenge(newNameChallenge.toLowerCase()).isPresent()) {
                    throw new IllegalArgumentException("Challenge with the name already exists.");
                }
            }
            currentChallenge.setNameChallenge(updatedChallenge.getNameChallenge());
            return challengeRepository.save(currentChallenge);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }

    public void delete(Long id, String role) {
        Challenge challengeToDelete = challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Challenge not found with id: " + id));

        if (role.equals("[ROLE_ADMIN]")) {
            challengeRepository.deleteById(challengeToDelete.getId());
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }
}
