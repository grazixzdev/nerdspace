package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Creator;
import br.com.lucena.nerdspace.model.Movie;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaMovieDetailed(
        Long id,
        String title,
        String translatedTitle,
        String synopsis,
        Set<Creator> creators,
        Type type,
        Set<Genre> genres,
        double rating,
        LocalDate releaseDate,
        int durationMinutes,
        String director,
        String productionCompany,
        String image
) {

    public DataMediaMovieDetailed(Movie film) {
        this(film.getId(), film.getTitle(), film.getTranslatedTitle(), film.getSynopsis(), film.getCreators(), film.getType(),
             film.getGenre(), film.getRating(), film.getReleaseDate(), film.getDurationMinutes(), film.getDirector(), film.getProductionCompany(), film.getImage());
    }
}
