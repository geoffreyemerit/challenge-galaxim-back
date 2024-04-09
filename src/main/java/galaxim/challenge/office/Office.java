package galaxim.challenge.office;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.brand.Brand;
//import galaxim.challenge.city.City;
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

    // Dans la classe qui est dirigée !
    //@ManyToOne
    //@JsonIgnoreProperties("cityList")
    //private City city;

    @ManyToOne
    @JsonIgnoreProperties("brandList")
    private Brand brand;

    // Dans la classe Recipe (celle qui dirige)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "office_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("office")
    private List<User> userList = new ArrayList<>();  /*équivaut à [] */

}
