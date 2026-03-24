package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Media;
import br.com.lucena.nerdspace.model.enums.Type;

public record DataMediaList(
        Long id,
        String title,
        String translatedTitle,
        String image,
        Type type
) {
    public DataMediaList(Media media){
        this(media.getId(), media.getTitle(), media.getTranslatedTitle(), media.getImage(), media.getType());
    }
}
