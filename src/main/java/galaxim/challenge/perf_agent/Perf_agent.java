package galaxim.challenge.perf_agent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.office.Office;
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
public class Perf_agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer sales_ssp;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("performanceAgentList")
    private Performance performance;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("performanceAgentList")
    private Office office;
}
