package galaxim.challenge.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.challenge.Challenge;
import galaxim.challenge.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name_job;

    // Dans la classe qui dirige
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "job_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("job")
    private List<Challenge> challengeList = new ArrayList<>();  /*équivaut à [] */

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "job_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("job")
    private List<User> userList = new ArrayList<>();  /*équivaut à [] */

}
