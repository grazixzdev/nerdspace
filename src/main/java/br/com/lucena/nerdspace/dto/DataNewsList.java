package br.com.lucena.nerdspace.dto;

import br.com.lucena.nerdspace.model.News;

import java.time.LocalDateTime;

public record DataNewsList(
        Long id,
        String title,
        String subTitle,
        LocalDateTime timePosted,
        String image
) {
    public DataNewsList(News news) {
        this(news.getId(), news.getTitle(), news.getSubTitle(), news.getTimePosted(), news.getImage());
    }
}
