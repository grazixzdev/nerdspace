package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Anime;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Status;
import br.com.lucena.nerdspace.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaAnimeList(
        Long id,
        String title,
        String translatedTitle,
        String image,
        Type type
) {
    public DataMediaAnimeList(Anime anime) {
        this(anime.getId(), anime.getTitle(), anime.getTranslatedTitle(),
                anime.getImage(), anime.getType());
    }
}
