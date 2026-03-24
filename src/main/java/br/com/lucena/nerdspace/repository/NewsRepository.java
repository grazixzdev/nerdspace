package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findAllByOrderByTimePostedDesc(Pageable pageable);
}
