package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaSerieDetailed;
import br.com.lucena.nerdspace.dto.DataMediaSerieList;
import br.com.lucena.nerdspace.model.Serie;
import br.com.lucena.nerdspace.repository.SerieRepository;
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
@RequestMapping("/serie")
public class SerieController {
    public final SerieRepository serieRepository;

    public SerieController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity viewMediaSerie(@PathVariable Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            return ResponseEntity.ok(new DataMediaSerieDetailed(serie.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaSerieList>> serieList (@PageableDefault Pageable pageable) {
        var page = serieRepository.findAllByActiveTrue(pageable)
                .map(DataMediaSerieList::new);
        return ResponseEntity.ok(page);
    }
}
