package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Manga;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaMangaList(
        Long id,
        String title,
        String translatedTitle,
        Set<Genre> genres,
        LocalDate releaseDate,
        String image,
        Type type
) {
    public DataMediaMangaList(Manga manga) {
        this(manga.getId(), manga.getTitle(), manga.getTranslatedTitle(), manga.getGenre(),
             manga.getReleaseDate(), manga.getImage(), manga.getType());
    }
}
