package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.Hq;
import br.com.lucena.nerdspace.model.enums.Type;

public record DataMediaHqList(
        Long id,
        String title,
        String translatedTitle,
        String image,
        Type type
) {
    public DataMediaHqList (Hq hq) {
        this(hq.getId(), hq.getTitle(), hq.getTranslatedTitle(), hq.getImage(), hq.getType());
    }
}
