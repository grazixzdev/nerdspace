package br.com.lucena.nerdspace.controller;

import br.com.lucena.nerdspace.dto.DataMediaGameDetailed;
import br.com.lucena.nerdspace.dto.DataMediaGameList;
import br.com.lucena.nerdspace.model.Game;
import br.com.lucena.nerdspace.repository.GameRepository;
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
@RequestMapping("/jogo")
public class GameController {
    public final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity viewMediaGame(@PathVariable Long id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isPresent()) {
            return ResponseEntity.ok(new DataMediaGameDetailed(game.get()));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<DataMediaGameList>> gameList(@PageableDefault Pageable pageable) {
        var page = gameRepository.findAllByActiveTrue(pageable)
                .map(DataMediaGameList::new);
        return ResponseEntity.ok(page);
    }
}
