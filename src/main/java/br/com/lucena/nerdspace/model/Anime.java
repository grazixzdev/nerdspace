package br.com.lucena.nerdspace.model;

import br.com.lucena.nerdspace.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "anime")
@Getter
@Setter
public class Anime extends Media{
    private int seasons;
    private int episodes;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String studio;
    private String streamingService;
}
