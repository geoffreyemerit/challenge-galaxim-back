package galaxim.challenge.job;

import galaxim.challenge.challenge.ChallengeRepository;
import galaxim.challenge.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    public List<Job> getAll(String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return jobRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Job getById(Long id, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            return jobRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public Job add(Job job, String role) {
        if (role.equals("[ROLE_ADMIN]")) {
            if (job.getNameJob() != null && jobRepository.findByNameJob(job.getNameJob().toLowerCase()).isPresent()) {
                throw new IllegalArgumentException("Job already exists.");
            }

            job.setNameJob(job.getNameJob());

            return jobRepository.save(job);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }


    public Job update(Job updatedJob, String role) {
        Job currentJob = jobRepository.findById(updatedJob.getId())
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + updatedJob.getId()));

        if (role.equals("[ROLE_ADMIN]")) {
            String newNameJob = updatedJob.getNameJob();
            if(newNameJob  != null && !newNameJob.equalsIgnoreCase(currentJob.getNameJob())){
                if (jobRepository.findByNameJob(newNameJob.toLowerCase()).isPresent()) {
                    throw new IllegalArgumentException("Job with the name already exists.");
                }
            }

            currentJob.setNameJob(updatedJob.getNameJob());

            return jobRepository.save(currentJob);
        } else {
            throw new AccessDeniedException("User does not have the correct rights to update this resource");
        }
    }

    public void delete(Long id, String role) {
        Job jobToDelete = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        if (role.equals("[ROLE_ADMIN]")) {
            if (!userRepository.existsByJob(jobToDelete) || !challengeRepository.existsByJob(jobToDelete)) {
                jobRepository.delete(jobToDelete);
            } else {
                throw new RuntimeException("Cannot delete job because users are associated with it");
            }
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access this resource");
        }
    }
}

