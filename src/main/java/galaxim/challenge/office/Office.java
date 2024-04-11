package galaxim.challenge.office;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.brand.Brand;
import galaxim.challenge.city.City;
import galaxim.challenge.perfsAgent.PerfsAgent;
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
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_office", nullable = false)
    private String nameOffice;

    @ManyToOne
    @JsonIgnoreProperties({"cityList", "officeList"})
    private City city;

    @ManyToOne
    @JsonIgnoreProperties({"brandList", "officeList"})
    private Brand brand;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"userList", "perfsAgentList", "office"})
    private List<User> userList = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"userList", "office"})
    private List<PerfsAgent> perfsAgentList = new ArrayList<>();
}
