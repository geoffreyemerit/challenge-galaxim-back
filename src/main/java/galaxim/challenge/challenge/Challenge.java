package galaxim.challenge.challenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.job.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name_challenge;
    private String desc;
    private String logo_challenge;
    private Date start_challenge;
    private Date end_challenge;
    private Date start_perf;
    private Date end_perf;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("jobList")
    private Job job;

}
