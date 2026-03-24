package br.com.lucena.nerdspace.model.enums;

public enum Genre {
    ACTION("Ação"),
    ADVENTURE("Aventura"),
    COMEDY("Comédia"),
    DRAMA("Drama"),
    FANTASY("Fantasia"),
    HORROR("Terror"),
    MYSTERY("Mistério"),
    ROMANCE("Romance"),
    SCI_FI("Ficção Científica"),
    SLICE_OF_LIFE("Slice of Life"),
    SHONEN("Shonen"),
    SEINEN("Seinen"),
    SHOUJO("Shoujo"),
    SAMURAI("Samurai"),
    THRILLER("Suspense"),
    CYBERPUNK("Cyberpunk"),
    ISEKAI("Isekai"),
    SUPERHERO("Super-Herói"),
    RPG("RPG"),
    SPACE("Espaço"),
    PSYCHOLOGICAL("Psicológico"),
    SPORTS("Esportes"),
    SUPERNATURAL("Supernatural"),
    HISTORICAL("Histórico"),
    MECHA("Mecha"),
    PARODY("Paródia"),
    DEMENTIA("Demência"),
    MILITARY("Militar"),
    KIDS("Crianças");

    private final String description;

    Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
