package galaxim.challenge.perfsMandatary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import galaxim.challenge.performance.Performance;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "perfs_mandatary")
public class PerfsMandatary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer recruiters;
    @Column(name = "ca_ht_ssp_network")
    private Integer caHtSspNetwork;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("perfMandataryList")
    @JsonProperty(required = true)
    private Performance performance;
}

