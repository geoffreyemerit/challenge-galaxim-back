package galaxim.challenge.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import galaxim.challenge.office.Office;
import galaxim.challenge.performance.Performance;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String photo;
    private String job;
    private Integer caHtAct;
    private Integer caHtSsp;
    private Integer salesSsp;
    private Integer mandates;
    private Integer bestDev;
    private Integer caHtNetworkTeamSsp;

    @JsonIgnore
    private String password;

    @ManyToOne
    @JsonIgnoreProperties("userList")
    private Office office;

    // Dans la classe User (celle qui dirige)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")  /* Clé étrangère */
    @JsonIgnoreProperties("user")
    private List<Performance> performanceList = new ArrayList<>();  /*équivaut à [] */


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role)); // un role deviendra une authority
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
