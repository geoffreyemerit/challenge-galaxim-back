package galaxim.challenge.challenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.job.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_challenge", nullable = false)
    private String nameChallenge;
    @Column(name = "desc_challenge")
    private String descChallenge;
    @Column(name = "logo_challenge")
    private String logoChallenge;
    @Column(name = "start_challenge")
    private Date startChallenge;
    @Column(name = "end_challenge")
    private Date endChallenge;
    @Column(name = "start_perf")
    private Date startPerf;
    @Column(name = "end_perf")
    private Date endPerf;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("challengeList")
    private Job job;
}