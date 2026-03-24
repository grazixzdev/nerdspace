package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Book;
import br.com.lucena.nerdspace.model.Creator;
import br.com.lucena.nerdspace.model.enums.BookFormat;
import br.com.lucena.nerdspace.model.enums.Genre;
import br.com.lucena.nerdspace.model.enums.Type;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.Set;

public record DataMediaBookDetailed(
        Long id,
        String title,
        String translatedTitle,
        String synopsis,
        Set<Creator> creators,
        Type type,
        Set<Genre> genres,
        double rating,
        LocalDate releaseDate,
        int pagesCount,
        String serialization,
        String publisher,
        String language,
        BookFormat format,
        String edition,
        String isbn,
        String image
) {
    public DataMediaBookDetailed (Book book) {
        this(book.getId(), book.getTitle(), book.getTranslatedTitle(), book.getSynopsis(), book.getCreators(), book.getType(),
             book.getGenre(), book.getRating(), book.getReleaseDate(), book.getPagesCount(), book.getSerialization(), book.getPublisher(),
                book.getLanguage(), book.getFormat(), book.getEdition(), book.getIsbn(), book.getImage());
    }
}
