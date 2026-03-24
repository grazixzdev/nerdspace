package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.News;

import java.time.LocalDateTime;

public record DataNewsDetailed(
        Long id,
        String title,
        String subTitle,
        String content,
        LocalDateTime timePosted,
        String image
) {
    public DataNewsDetailed(News news) {
        this(news.getId(), news.getTitle(), news.getSubTitle(), news.getContent(), news.getTimePosted(), news.getImage());
    }
}
