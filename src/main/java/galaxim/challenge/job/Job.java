package galaxim.challenge.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "job_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("job")
    private List<User> userList = new ArrayList<>();  /*équivaut à [] */

}
