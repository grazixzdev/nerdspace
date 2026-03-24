package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataNewsDetailed;
import br.com.lucena.nerdspace.dto.DataNewsList;
import br.com.lucena.nerdspace.model.News;
import br.com.lucena.nerdspace.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/noticia")
public class NewsController {
    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity viewNews(@PathVariable Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            return ResponseEntity.ok(new DataNewsDetailed(news.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataNewsList>> newsList(@PageableDefault Pageable pageable) {
        var page = newsRepository.findAllByOrderByTimePostedDesc(pageable)
                .map(DataNewsList::new);
        return ResponseEntity.ok(page);
    }
}
