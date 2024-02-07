package galaxim.challenge.office;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String nameOffice;
    private String city;
    private Integer caHtOfficeSsp;

    // Dans la classe Recipe (celle qui dirige)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "office_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("office")
    private List<User> userList = new ArrayList<>();  /*équivaut à [] */

}