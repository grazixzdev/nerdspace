package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.Anime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Page<Anime> findAllByActiveTrue(Pageable pageable);

}
