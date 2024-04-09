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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("performanceList")
    @JsonProperty(required = true) // Rend la propriété obligatoire
    private User user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "performance_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("performance")
    private List<PerfsAgent> perfAgentList = new ArrayList<>();  /*équivaut à [] */

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "performance_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("performance")
    private List<PerfsMandatary> perfMandataryList = new ArrayList<>();  /*équivaut à [] */

}