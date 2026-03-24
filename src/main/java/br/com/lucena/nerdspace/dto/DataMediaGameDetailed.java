package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Creator;
import br.com.lucena.nerdspace.model.Game;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Plataform;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaGameDetailed(
        Long id,
        String title,
        String translatedTitle,
        String synopsis,
        Set<Creator> creators,
        Type type,
        Set<Genre> genres,
        double rating,
        LocalDate releaseDate,
        Set<Plataform> plataform,
        String developer,
        String publisher,
        int playtimeHours,
        String image
) {
    public DataMediaGameDetailed (Game game) {
        this(game.getId(), game.getTitle(), game.getTranslatedTitle(), game.getSynopsis(), game.getCreators(), game.getType(),
             game.getGenre(), game.getRating(), game.getReleaseDate(), game.getPlataform(), game.getDeveloper(), game.getPublisher(),
                game.getPlaytimeHours(), game.getImage());
    }
}
