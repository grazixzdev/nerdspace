package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaMovieDetailed;
import br.com.lucena.nerdspace.dto.DataMediaMovieList;
import br.com.lucena.nerdspace.model.Movie;
import br.com.lucena.nerdspace.repository.MovieRepository;
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
@RequestMapping("/filme")
public class MovieController {
    public final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity viewMediaMovie(@PathVariable Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(new DataMediaMovieDetailed(movie.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaMovieList>> movieList(@PageableDefault Pageable pageable) {
        var page = movieRepository.findAllByActiveTrue(pageable)
                .map(DataMediaMovieList::new);
        return ResponseEntity.ok(page);
    }
}
