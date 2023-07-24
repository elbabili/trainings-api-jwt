package fr.fms.api.trainings.security.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)  // on veut que l'utilisateur soit unique
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // équivalent à JsonIgnore afin donc de ne pas renvoyer la valeur du mot de passe
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)    //on veut charger tous les roles de l'utilisateur
    private Collection<AppRole> roles = new ArrayList<>();  //dans ce cas, il faut impérativement initialiser notre collection
}