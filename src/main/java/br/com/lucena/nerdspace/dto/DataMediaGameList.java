package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Game;
import br.com.lucena.nerdspace.model.enums.Type;

public record DataMediaGameList(
        Long id,
        String title,
        String translatedTitle,
        String image,
        Type type
) {
    public DataMediaGameList (Game game) {
        this(game.getId(), game.getTitle(), game.getTranslatedTitle(), game.getImage(), game.getType());
    }
}
