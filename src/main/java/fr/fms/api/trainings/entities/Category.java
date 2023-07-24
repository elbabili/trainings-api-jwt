package fr.fms.api.trainings.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Collection<Training> trainings;
    /*
    * FetchType.LAZY : est le mode par défaut, la collection de formations ici ne sera pas chargé (ou uniquement si besoin)
    * FetchType.EAGER : indique que toutes les formations sont chargées en même temps que l'entité qui la porte (pb de perf)
    */
}
