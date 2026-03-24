package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Creator;
import br.com.lucena.nerdspace.model.Serie;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Status;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaSerieDetailed(
        Long id,
        String title,
        String translatedTitle,
        String synopsis,
        Set<Creator> creators,
        Type type,
        Set<Genre> genres,
        double rating,
        LocalDate releaseDate,
        int seasos,
        int episodes,
        Status status,
        String studio,
        String streamingService,
        String image
) {
    public DataMediaSerieDetailed(Serie serie) {
        this(serie.getId(), serie.getTitle(), serie.getTranslatedTitle(), serie.getSynopsis(), serie.getCreators(), serie.getType(),
             serie.getGenre(), serie.getRating(), serie.getReleaseDate(), serie.getSeasons(), serie.getEpisodes(), serie.getStatus(),
                serie.getStudio(), serie.getStreamingService(), serie.getImage());
    }
}
