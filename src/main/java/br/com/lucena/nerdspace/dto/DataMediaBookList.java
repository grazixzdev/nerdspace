package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Book;
import br.com.lucena.nerdspace.model.enums.Type;

public record DataMediaBookList(
        Long id,
        String title,
        String translatedTitle,
        String image,
        Type type
) {
    public DataMediaBookList(Book book) {
        this(book.getId(), book.getTitle(), book.getTranslatedTitle(), book.getImage(), book.getType());
    }
}
