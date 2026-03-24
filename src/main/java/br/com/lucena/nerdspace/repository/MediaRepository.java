package br.com.lucena.nerdspace.repository;

import br.com.lucena.nerdspace.model.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface MediaRepository extends JpaRepository<Media, Long> {
    Page<Media> findAllByActiveTrue(Pageable pageable);

    Page<Media> findAllByActiveTrueAndReleaseDateBetween(
            LocalDate start,
            LocalDate end,
            Pageable pageable
    );

    Page<Media> findAllByActiveTrueAndRatingGreaterThanEqual(
            Double rating,
            Pageable pageable
    );

    @Query("SELECT m FROM Media m WHERE m.active = true AND " +
            "(LOWER(m.title) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(m.translatedTitle) LIKE LOWER(CONCAT('%', :termo, '%')))")
    Page<Media> searchByTitle(String termo, Pageable pageable);

    @Query("SELECT m FROM Media m WHERE m.active = true " +
            "AND (:year IS NULL OR YEAR(m.releaseDate) >= :year) " +
            "AND (:rating IS NULL OR m.rating >= :rating)")
    Page<Media> findByFilters(Integer year, Double rating, Pageable pageable);
}
