package br.com.lucena.nerdspace;

import br.com.lucena.nerdspace.controller.MediaController;
import br.com.lucena.nerdspace.dto.DataMediaAnimeDetailed;
import br.com.lucena.nerdspace.model.Anime;
import br.com.lucena.nerdspace.model.Media;
import br.com.lucena.nerdspace.model.enums.Type;
import br.com.lucena.nerdspace.repository.GameRepository;
import br.com.lucena.nerdspace.repository.MediaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MediaController.class)
class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MediaRepository repository;

    @MockitoBean
    private GameRepository gameRepository;

    @Test
    @DisplayName("Deveria devolver código http 200 para pesquisa válida")
    void testPesquisaMedia() throws Exception {
        when(repository.searchByTitle(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/media/pesquisa")
                        .param("term", "Naruto"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria devolver código http 200 para filtragem com parâmetros")
    void testFiltragemMedia() throws Exception {
        when(repository.findByFilters(any(), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/media/filtragem")
                        .param("year", "2024")
                        .param("minRating", "8.5"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria retornar 200 ao buscar lista de recentes")
    void testListaRecentes() throws Exception {
        when(repository.findAllByActiveTrueAndReleaseDateBetween(any(), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/media/lista-recentes"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria retornar 200 ao buscar lista de populares")
    void testListaPopulares() throws Exception {
        when(repository.findAllByActiveTrueAndRatingGreaterThanEqual(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/media/lista-populares"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria retornar 400 (Bad Request) quando o termo de pesquisa estiver ausente")
    void testPesquisaSemParametro() throws Exception {
        mockMvc.perform(get("/media/pesquisa"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deveria aceitar parâmetros de paginação na URL")
    void testPaginacaoCustomizada() throws Exception {
        when(repository.findAllByActiveTrue(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/media/lista")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria retornar JSON com os campos corretos do DTO")
    void testEstruturaJsonRetorno() throws Exception {
        var media = new Media();
        media.setId(1L);
        media.setTitle("Naruto");
        media.setTranslatedTitle("Naruto Clássico");
        media.setType(Type.ANIME);
        media.setImage("capa.jpg");
        media.setActive(true);

        var paginaEntidade = new PageImpl<>(List.of(media));

        when(repository.findAllByActiveTrue(any(Pageable.class))).thenReturn(paginaEntidade);

        mockMvc.perform(get("/media/lista"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Naruto"))
                .andExpect(jsonPath("$.content[0].type").value("ANIME"))
                .andExpect(jsonPath("$.content[0].image").value("capa.jpg"));
    }

    @Test
    @DisplayName("Deveria retornar 400 quando o parâmetro de ano for inválido (texto em vez de número)")
    void testFiltragemComAnoInvalido() throws Exception {
        mockMvc.perform(get("/media/filtragem")
                        .param("year", "texto-invalido")
                        .param("minRating", "8.5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deveria retornar lista vazia e status 200 quando não houver mídias populares")
    void testListaPopularesVazia() throws Exception {
        when(repository.findAllByActiveTrueAndRatingGreaterThanEqual(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/media/lista-populares"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @DisplayName("Deveria converter Entity para DTO corretamente tratando listas vazias")
    void testConversaoDetailedDto() {
        var anime = new Anime();
        anime.setTitle("Death Note");
        anime.setCreators(Set.of());

        var dto = new DataMediaAnimeDetailed(anime);

        assertThat(dto.title()).isEqualTo("Death Note");
        assertThat(dto.creators()).isEmpty();
    }

    @Test
    @DisplayName("Deveria retornar 404 ao buscar jogo inexistente")
    void testGameNotFound() throws Exception {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/jogo/999"))
                .andExpect(status().isNotFound());
    }
}