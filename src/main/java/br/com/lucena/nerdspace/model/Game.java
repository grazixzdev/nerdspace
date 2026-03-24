package br.com.lucena.nerdspace.model;

import br.com.lucena.nerdspace.model.enums.Plataform;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "game")
@Getter
@Setter
public class Game extends Media{
    @ElementCollection(targetClass =  Plataform.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "plataforms")
    private Set<Plataform> plataform;
    private String developer;
    private String publisher;
    private int playtimeHours;
}
