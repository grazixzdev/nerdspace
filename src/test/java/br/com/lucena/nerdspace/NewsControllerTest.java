package br.com.lucena.nerdspace;

import br.com.lucena.nerdspace.controller.NewsController;
import br.com.lucena.nerdspace.model.News;
import br.com.lucena.nerdspace.repository.NewsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.*;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NewsRepository repository;

    @Test
    @DisplayName("Deveria retornar 200 e a notícia correta ao buscar por ID")
    void testViewNewsSucesso() throws Exception {
        News news = new News();
        news.setId(1L);
        news.setTitle("Novo jogo anunciado!");

        when(repository.findById(1L)).thenReturn(Optional.of(news));

        mockMvc.perform(get("/noticia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Novo jogo anunciado!"));
    }

    @Test
    @DisplayName("Deveria retornar 404 ao buscar notícia inexistente")
    void testViewNewsNotFound() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/noticia/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deveria listar notícias ordenadas por data")
    void testListarNoticias() throws Exception {
        when(repository.findAllByOrderByTimePostedDesc(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new News())));

        mockMvc.perform(get("/noticia/lista"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}