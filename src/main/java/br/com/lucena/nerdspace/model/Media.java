package br.com.lucena.nerdspace.model;

import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "media")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String translatedTitle;
    @Column(columnDefinition = "TEXT")
    private String synopsis;
    @ManyToMany
    @JoinTable(
            name = "media_creators",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "creator_id")
    )
    private Set<Creator> creators = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Type type;
    @ElementCollection(targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "genres")
    private Set<Genre> genre;
    private double rating;
    @Column(unique = true)
    private String image;
    private LocalDate releaseDate;
    private boolean active = true;

}
