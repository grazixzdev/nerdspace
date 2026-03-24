package br.com.lucena.nerdspace.model.enums;

public enum Status {
    RELEASING("Em Lançamento"),
    FINISHED("Finalizado"),
    HIATUS("Em Hiato"),
    CANCELLED("Cancelado"),
    UPCOMING("A Anunciar");

    private final String description;
    Status(String description) { this.description = description; }
    public String getDescription() { return description; }
}
