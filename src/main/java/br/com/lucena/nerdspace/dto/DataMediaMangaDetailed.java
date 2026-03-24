package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Creator;
import br.com.lucena.nerdspace.model.Manga;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaMangaDetailed(
        Long id,
        String title,
        String translatedTitle,
        String synopsis,
        Set<Creator> creators,
        Type type,
        Set<Genre> genres,
        double rating,
        LocalDate releaseDate,
        int volumesCount,
        int chaptersCount,
        String serialization,
        String isbn,
        String image
) {
    public DataMediaMangaDetailed(Manga manga) {
        this(manga.getId(), manga.getTitle(), manga.getTranslatedTitle(), manga.getSynopsis(), manga.getCreators(),
        manga.getType(), manga.getGenre(), manga.getRating(), manga.getReleaseDate(), manga.getVolumesCount(), manga.getChaptersCount(),
        manga.getSerialization(), manga.getIsbn(), manga.getImage());
    }
}
