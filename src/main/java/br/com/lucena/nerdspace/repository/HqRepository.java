package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.Hq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HqRepository extends JpaRepository<Hq, Long> {
    Page<Hq> findAllByActiveTrue(Pageable pageable);
}
