package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Creator;
import br.com.lucena.nerdspace.model.Hq;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Type;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaHqDetailed(
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
    public DataMediaHqDetailed (Hq hq) {
        this(hq.getId(), hq.getTitle(), hq.getTranslatedTitle(), hq.getSynopsis(), hq.getCreators(), hq.getType(), hq.getGenre(),
             hq.getRating(), hq.getReleaseDate(), hq.getVolumesCount(), hq.getChaptersCount(), hq.getSerialization(), hq.getIsbn(), hq.getImage());
    }
}
