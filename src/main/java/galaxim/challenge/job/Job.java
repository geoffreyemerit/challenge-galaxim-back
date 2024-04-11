package galaxim.challenge.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.challenge.Challenge;
import galaxim.challenge.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_job", nullable = false)
    private String nameJob;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "job_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties({"userList", "challengeList", "job"})
    private List<User> userList = new ArrayList<>();  /*équivaut à [] */

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "job_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties({"userList", "job"})
    private List<Challenge> challengeList = new ArrayList<>();  /*équivaut à [] */

}
