package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Anime;
import br.com.lucena.nerdspace.model.Creator;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Status;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaAnimeDetailed(
        Long id,
        String title,
        String translatedTitle,
        String synopsis,
        Set<Creator> creators,
        Type type,
        Set<Genre> genres,
        double rating,
        LocalDate releaseDate,
        int seasons,
        int episodes,
        Status status,
        String studio,
        String streamingService,
        String image
        ) {

    public DataMediaAnimeDetailed(Anime anime){
        this(anime.getId(), anime.getTitle(), anime.getTranslatedTitle(), anime.getSynopsis(),
                anime.getCreators(), anime.getType(), anime.getGenre(), anime.getRating(), anime.getReleaseDate(),
                anime.getSeasons(), anime.getEpisodes(), anime.getStatus(), anime.getStudio(),
                anime.getStreamingService(), anime.getImage()
        );
    }
}
