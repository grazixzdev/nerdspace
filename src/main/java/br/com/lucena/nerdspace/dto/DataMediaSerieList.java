package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Serie;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaSerieList(
        Long id,
        String title,
        String translatedTitle,
        Set<Genre> genres,
        LocalDate releaseDate,
        String image,
        Type type
) {

    public DataMediaSerieList (Serie serie) {
        this(serie.getId(), serie.getTitle(), serie.getTranslatedTitle(), serie.getGenre(), serie.getReleaseDate(), serie.getImage(), serie.getType());
    }
}
