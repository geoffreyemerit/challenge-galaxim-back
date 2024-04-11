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
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_brand", nullable = false)
    private String nameBrand;
    @Column(name = "logo_brand")
    private String logoBrand;

    // Dans la classe qui dirige
    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "brand_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties({"brand", "userList", "perfsAgentList" })
    private List<Office> officeList = new ArrayList<>();  /*équivaut à [] */

}