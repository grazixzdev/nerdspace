package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findAllByActiveTrue(Pageable pageable);
}
