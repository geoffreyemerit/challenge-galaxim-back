package galaxim.challenge.auth;

import galaxim.challenge.job.Job;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String required_role = "ROLE_USER";
    private Boolean isActive = true;
    private Job job;
}