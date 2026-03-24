package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaMangaDetailed;
import br.com.lucena.nerdspace.dto.DataMediaMangaList;
import br.com.lucena.nerdspace.model.Manga;
import br.com.lucena.nerdspace.repository.AnimeRepository;
import br.com.lucena.nerdspace.repository.MangaRepository;
import br.com.lucena.nerdspace.repository.MediaRepository;
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
@RequestMapping("/manga")
public class MangaController {
    private final MangaRepository mangaRepository;


    public MangaController(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity viewMediaManga(@PathVariable Long id){
        Optional<Manga> manga = mangaRepository.findById(id);

        if (manga.isPresent()) {
            return ResponseEntity.ok().body(new DataMediaMangaDetailed(manga.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaMangaList>> mangaList(@PageableDefault Pageable pageable){
        var page = mangaRepository.findAll(pageable)
                .map(DataMediaMangaList::new);
        return ResponseEntity.ok().body(page);
    }

}
