package galaxim.challenge.perfsAgent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import galaxim.challenge.office.Office;
import galaxim.challenge.performance.Performance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "perfs_agent")
public class PerfsAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sales_ssp")
    private Integer salesSsp;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties({"perfAgentList", "performance"})
    @JsonProperty(required = true)
    private Performance performance;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties({"office", "perfAgentList"})
    @JsonProperty(required = true)
    private Office office;

}