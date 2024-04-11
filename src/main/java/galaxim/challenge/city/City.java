package galaxim.challenge.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.office.Office;
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
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    private Integer zip;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "city_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties({"city", "userList", "perfsAgentList"})
    private List<Office> officeList = new ArrayList<>();  /*équivaut à [] */

}
