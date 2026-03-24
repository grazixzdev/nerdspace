package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Long> {
    Page<Manga> findAllByActiveTrue(Pageable pageable);
}
