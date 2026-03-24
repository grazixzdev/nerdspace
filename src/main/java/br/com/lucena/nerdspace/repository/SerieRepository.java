package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.Serie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Page<Serie> findAllByActiveTrue(Pageable pageable);
}
