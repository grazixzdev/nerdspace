package br.com.lucena.nerdspace.model.enums;

public enum BookFormat {
    HARDCOVER("Capa Dura"),
    PAPERBACK("Brochura"),
    DIGITAL("E-book / Digital"),
    AUDIOBOOK("Audiobook"),
    LEITURA_ONLINE("Webtoon / Online");

    private final String description;

    BookFormat(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
