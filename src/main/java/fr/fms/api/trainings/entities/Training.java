package fr.fms.api.trainings.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Training implements Serializable {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String photo;

    @ManyToOne
    private Category category;
}
