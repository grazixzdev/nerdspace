package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAllByActiveTrue(Pageable pageable);
}
