package br.com.lucena.nerdspace;

import br.com.lucena.nerdspace.dto.DataMediaList;
import br.com.lucena.nerdspace.model.*;
import br.com.lucena.nerdspace.model.enums.Plataform;
import br.com.lucena.nerdspace.model.enums.Status;
import br.com.lucena.nerdspace.model.enums.Type;
import br.com.lucena.nerdspace.repository.MediaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.autoconfigure.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class MediaRepositoryTest {

    @Autowired
    private MediaRepository repository;

    @Test
    @DisplayName("Deveria retornar medias filtradas por ano e nota")
    void findByFiltersCenario1() {
        var pageable = PageRequest.of(0, 10);
        var resultado = repository.findByFilters(2020, 8.0, pageable);

        assertThat(resultado).isNotNull();
    }

    @Test
    @DisplayName("Deveria persistir e recuperar um Movie com campos específicos")
    void testPersistenciaMovie() {
        Movie movie = new Movie();
        movie.setTitle("Interstellar");
        movie.setDurationMinutes(169);
        movie.setDirector("Christopher Nolan");
        movie.setType(Type.MOVIE);

        repository.save(movie);

        Movie salvo = (Movie) repository.findById(movie.getId()).get();
        assertThat(salvo.getDirector()).isEqualTo("Christopher Nolan");
        assertThat(salvo.getDurationMinutes()).isEqualTo(169);
    }

    @Test
    @DisplayName("Deveria encontrar mídias ignorando maiúsculas e minúsculas no título")
    void testSearchByTitleCaseInsensitive() {
        Media m = new Media();
        m.setTitle("ONE PIECE");
        m.setActive(true);
        repository.save(m);

        var resultado = repository.searchByTitle("one piece", PageRequest.of(0, 10));

        assertThat(resultado.getContent()).isNotEmpty();
        assertThat(resultado.getContent().get(0).getTitle()).isEqualTo("ONE PIECE");
    }

    @Test
    @DisplayName("Deveria mapear corretamente os dados da Entity para o DTO")
    void testMapeamentoDto() {
        Media media = new Media();
        media.setId(1L);
        media.setTitle("Naruto");
        media.setImage("capa.jpg");
        media.setType(Type.ANIME);

        DataMediaList dto = new DataMediaList(media);

        assertEquals(media.getTitle(), dto.title());
        assertEquals(media.getType(), dto.type());
    }

    @Test
    @DisplayName("Deveria retornar todas as mídias quando os filtros de ano e nota forem nulos")
    void findByFiltersCenarioParametrosNulos() {
        Media m = new Media();
        m.setTitle("Filtro Nulo Teste");
        m.setActive(true);
        repository.save(m);

        var pageable = PageRequest.of(0, 10);
        var resultado = repository.findByFilters(null, null, pageable);

        assertThat(resultado.getContent()).isNotEmpty();
    }

    @Test
    @DisplayName("Não deveria retornar mídias que estão com active = false")
    void testSearchByTitleApenasAtivos() {
        Media m = new Media();
        m.setTitle("Filme Escondido");
        m.setActive(false);
        repository.save(m);

        var resultado = repository.searchByTitle("Filme Escondido", PageRequest.of(0, 10));

        assertThat(resultado.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Deveria persistir um Game com múltiplas plataformas")
    void testPersistenciaGamePlataformas() {
        Game game = new Game();
        game.setTitle("The Witcher 3");
        game.setPlataform(Set.of(Plataform.PC, Plataform.PS4, Plataform.XBOX_ONE));
        game.setDeveloper("CD Projekt Red");
        game.setType(Type.GAME);

        repository.save(game);

        Game salvo = (Game) repository.findById(game.getId()).get();
        assertThat(salvo.getPlataform()).hasSize(3);
        assertThat(salvo.getPlataform()).contains(Plataform.PC);
    }

    @Test
    @DisplayName("Não deveria permitir salvar dois livros com o mesmo ISBN")
    void testUniqueIsbn() {
        Book book1 = new Book();
        book1.setTitle("Livro 1");
        book1.setIsbn("123456789");
        repository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Livro 2");
        book2.setIsbn("123456789");

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            repository.save(book2);
            repository.flush();
        });
    }

    @Test
    @DisplayName("Deveria salvar e recuperar o status correto de um Anime")
    void testStatusAnime() {
        Anime anime = new Anime();
        anime.setTitle("Naruto");
        anime.setStatus(Status.FINISHED);
        anime.setType(Type.ANIME);

        repository.save(anime);

        Anime salvo = (Anime) repository.findById(anime.getId()).get();
        assertEquals(Status.FINISHED, salvo.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar uma instância de Anime quando o tipo for ANIME")
    void testPolimorfismoAnime() {
        Anime anime = new Anime();
        anime.setTitle("Fullmetal Alchemist");
        anime.setType(Type.ANIME);
        anime.setSeasons(1);
        anime.setEpisodes(64);
        repository.save(anime);

        var resultado = repository.findAll(PageRequest.of(0, 10));
        Media mediaRecuperada = resultado.getContent().get(0);

        assertThat(mediaRecuperada).isInstanceOf(Anime.class);
        assertThat(((Anime) mediaRecuperada).getEpisodes()).isEqualTo(64);
    }

    @Test
    @DisplayName("Deveria salvar media e associar a múltiplos criadores")
    void testRelacionamentoCreators() {
        Creator autor = new Creator();
        autor.setName("Eiichiro Oda");

        Manga manga = new Manga();
        manga.setTitle("One Piece");
        manga.getCreators().add(autor);
        manga.setType(Type.MANGA);

        repository.save(manga);

        Manga salvo = (Manga) repository.findById(manga.getId()).get();
        assertThat(salvo.getCreators()).isNotEmpty();
        assertThat(salvo.getCreators().iterator().next().getName()).isEqualTo("Eiichiro Oda");
    }

    @Test
    @DisplayName("Deveria filtrar mídias exatamente pelo ano da data de lançamento")
    void testBuscaPorAnoEspecifico() {
        Media m1 = new Media();
        m1.setTitle("Mídia Antiga");
        m1.setReleaseDate(java.time.LocalDate.of(2022, 5, 15));
        m1.setActive(true);
        repository.save(m1);

        Media m2 = new Media();
        m2.setTitle("Mídia Recente");
        m2.setReleaseDate(java.time.LocalDate.of(2024, 1, 1));
        m2.setActive(true);
        repository.save(m2);

        var resultado = repository.findByFilters(2023, null, PageRequest.of(0, 10));

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getTitle()).isEqualTo("Mídia Recente");
    }

    @Test
    @DisplayName("Deveria retornar apenas mídias do ano de 2024 quando filtrado por esse ano")
    void findByFiltersCenarioAno2024() {
        Media media2024 = new Media();
        media2024.setTitle("Sucesso de 2024");
        media2024.setReleaseDate(java.time.LocalDate.of(2024, 5, 20));
        media2024.setRating(9.0);
        media2024.setActive(true);
        repository.save(media2024);

        Media media2020 = new Media();
        media2020.setTitle("Clássico de 2020");
        media2020.setReleaseDate(java.time.LocalDate.of(2020, 1, 1));
        media2020.setRating(9.0);
        media2020.setActive(true);
        repository.save(media2020);

        var pageable = PageRequest.of(0, 10);
        var resultado = repository.findByFilters(2024, 8.0, pageable);

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getTitle()).isEqualTo("Sucesso de 2024");
    }

    @Test
    @DisplayName("Deveria encontrar a mídia usando apenas uma parte do título (Case Insensitive)")
    void testSearchByTitleLikeOperator() {
        Media m1 = new Media();
        m1.setTitle("Naruto Shippuden");
        m1.setActive(true);
        repository.save(m1);

        Media m2 = new Media();
        m2.setTitle("One Piece");
        m2.setActive(true);
        repository.save(m2);

        var resultado = repository.searchByTitle("shippu", PageRequest.of(0, 10));

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getTitle()).contains("Naruto Shippuden");
    }
}