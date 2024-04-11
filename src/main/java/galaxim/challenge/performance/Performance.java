package galaxim.challenge.performance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import galaxim.challenge.perfsAgent.PerfsAgent;
import galaxim.challenge.perfsMandatary.PerfsMandatary;
import galaxim.challenge.user.User;
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
@Table(name = "performances")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "add_date")
    private Date addDate;
    @Column(name = "ca_ht_act")
    private Integer caHtAct;
    @Column(name = "ca_ht_ssp")
    private Integer caHtSsp;
    private Integer mandate;

    @ManyToOne
    @JsonIgnoreProperties("performanceList")
    @JsonProperty(required = true)
    private User user;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnoreProperties("performance")
    private PerfsAgent perfsAgent;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnoreProperties("performance")
    private PerfsMandatary perfsMandatary;
}