package br.com.lucena.nerdspace.model;

import br.com.lucena.nerdspace.model.enums.BookFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book extends Media {
    private int pagesCount;
    private String serialization;
    private String publisher;
    private String language;
    @Enumerated(EnumType.STRING)
    private BookFormat format;
    private String edition;
    @Column(unique = true)
    private String isbn;
}
