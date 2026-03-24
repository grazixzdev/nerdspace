package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Movie;
import br.com.lucena.nerdspace.model.enums.Type;

public record DataMediaMovieList(
        Long id,
        String title,
        String translatedTitle,
        String image,
        Type type
) {
    public DataMediaMovieList(Movie film) {
        this(film.getId(), film.getTitle(), film.getTranslatedTitle(), film.getImage(), film.getType());
    }
}
