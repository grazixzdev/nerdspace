package br.com.lucena.nerdspace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie extends Media {
    private int durationMinutes;
    private String director;
    private String productionCompany;
}
