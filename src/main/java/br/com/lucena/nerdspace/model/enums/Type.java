package br.com.lucena.nerdspace.model.enums;

public enum Type {
    GAME("Jogo"),
    ANIME("Anime"),
    SERIES("Série"),
    MOVIE("Filme"),
    MANGA("Mangá"),
    HQ("História em Quadrinhos"),
    BOOK("Livro");

    private final String description;

    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
