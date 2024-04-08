package galaxim.challenge.perf_mandatary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.performance.Performance;
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
public class Perf_mandatary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer recruiters;
    private Integer ca_ht_ssp_network;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("performanceMandataryList")
    private Performance performance;
}
