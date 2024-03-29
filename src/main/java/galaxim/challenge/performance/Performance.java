package galaxim.challenge.performance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.user.User;
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
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date addDate;
    private Integer caHtAct;
    private Integer caHtSsp;
    private Integer mandate;

    // Dans la classe qui est dirigée !
    @ManyToOne
    @JsonIgnoreProperties("performanceList")
    private User user;
}
