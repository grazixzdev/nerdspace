package br.com.lucena.nerdspace.model;

import br.com.lucena.nerdspace.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "serie")
@Getter
@Setter
public class Serie extends Media{
    private int seasons;
    private int episodes;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String studio;
    private String streamingService;
}
