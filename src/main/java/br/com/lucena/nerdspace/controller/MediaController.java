package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaList;
import br.com.lucena.nerdspace.repository.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaRepository mediaRepository;

    public MediaController(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaList>> mediaList(@PageableDefault Pageable pageable) {
        var page = mediaRepository.findAllByActiveTrue(pageable)
                .map(DataMediaList::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/lista-recentes")
    public ResponseEntity<Page<DataMediaList>> mediaRecentList(@PageableDefault Pageable pageable) {
        LocalDate dataInicio = LocalDate.of(2025, 1, 1);
        LocalDate dataFim = LocalDate.of(2026, 12, 31);
        var page = mediaRepository.findAllByActiveTrueAndReleaseDateBetween(dataInicio, dataFim, pageable)
                .map(DataMediaList::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/lista-populares")
    public ResponseEntity<Page<DataMediaList>> mediaPopularList(@PageableDefault Pageable pageable) {
        Double rating = 8.0;
        var page = mediaRepository.findAllByActiveTrueAndRatingGreaterThanEqual(rating, pageable)
                .map(DataMediaList::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<Page<DataMediaList>> mediaSearchList(@RequestParam String term, @PageableDefault Pageable pageable) {
        var page = mediaRepository.searchByTitle(term, pageable)
                .map(DataMediaList::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/filtragem")
    public ResponseEntity<Page<DataMediaList>> mediaSearchList(@RequestParam int year, @RequestParam double minRating, @PageableDefault Pageable pageable) {
        var page = mediaRepository.findByFilters(year, minRating, pageable)
                .map(DataMediaList::new);
        return ResponseEntity.ok(page);
    }
}
