package galaxim.challenge.brand;

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
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name_brand;
    private String logo_brand;

    // Dans la classe qui dirige
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "brand_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("brand")
    private List<Office> officeList = new ArrayList<>();  /*équivaut à [] */

}
