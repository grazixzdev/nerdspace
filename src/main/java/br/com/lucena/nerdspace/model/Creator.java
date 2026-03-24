package br.com.lucena.nerdspace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "creators")
@Getter
@Setter
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String nacionality;
    private String biography;
    @JsonIgnore
    @ManyToMany(mappedBy = "creators")
    private Set<Media> medias;
}
