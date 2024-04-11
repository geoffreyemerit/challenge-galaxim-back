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

    @OneToOne(mappedBy = "perfsAgent", cascade= CascadeType.ALL)
    @JsonIgnoreProperties(value = {"perfsAgent", "performance"})
    private Performance performance;

    @ManyToOne
    @JsonIgnoreProperties({"office", "performance"})
    @JsonProperty(required = true)
    private Office office;

}