package br.com.lucena.nerdspace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "manga")
@Getter
@Setter
public class Manga extends Media{
    private int volumesCount;
    private int chaptersCount;
    private String serialization;
    @Column(unique = true)
    private String isbn;
}
