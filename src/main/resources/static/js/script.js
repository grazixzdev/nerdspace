"use strict";

/* =========================================================
   CONFIGURAÇÕES GLOBAIS
   ========================================================= */

const API_BASE_URL = "http://localhost:8080";
const PLACEHOLDER_IMAGE = "https://via.placeholder.com/200x300?text=Sem+Capa";
const BANNER_INTERVAL_MS = 5000;

let noticiasBanner = [];
let indiceAtual = 0;
let intervaloBanner;

/* =========================================================
   COMPONENTES EXTERNOS
   ========================================================= */

/**
 * Carrega o header e o footer da página e, após isso,
 * habilita a interação dos elementos que dependem desses componentes.
 */
async function incluirComponentes() {
  try {
    const [headerRes, footerRes] = await Promise.all([
      fetch("header.html"),
      fetch("footer.html"),
    ]);

    if (!headerRes.ok) {
      throw new Error(`Falha ao carregar header.html (${headerRes.status})`);
    }

    if (!footerRes.ok) {
      throw new Error(`Falha ao carregar footer.html (${footerRes.status})`);
    }

    const cabecalho = document.querySelector(".cabecalho");
    const rodape = document.querySelector(".rodape");

    if (cabecalho) {
      cabecalho.innerHTML = await headerRes.text();
    }

    if (rodape) {
      rodape.innerHTML = await footerRes.text();
    }

    configurarEventosGlobais();
    configurarModalFiltro();
  } catch (error) {
    console.error("Erro ao carregar componentes:", error);
  }
}

/**
 * Ativa os eventos globais do header, mantendo a navegação consistente
 * mesmo depois de inserir conteúdo via fetch.
 */
function configurarEventosGlobais() {
  const btnBusca = document.getElementById("btn-search");
  const inputBusca = document.getElementById("main-search");

  if (btnBusca && inputBusca) {
    const executarBusca = () => {
      const termo = inputBusca.value.trim();

      if (!termo) return;

      window.location.href = `searchMedia.html?term=${encodeURIComponent(
        termo,
      )}`;
    };

    btnBusca.onclick = executarBusca;

    inputBusca.addEventListener("keydown", (event) => {
      if (event.key === "Enter") {
        executarBusca();
      }
    });
  }

  const btnAbrirFiltro = document
    .querySelector('img[alt="filtrar"]')
    ?.closest("button");

  if (btnAbrirFiltro) {
    btnAbrirFiltro.onclick = () => {
      const modal = document.getElementById("modal-filtro");

      if (modal) {
        modal.style.display = "block";
      }
    };
  }
}

/* =========================================================
   MODAL DE FILTRO
   ========================================================= */

/**
 * Configura o comportamento do modal de filtro sem depender
 * de estilos inline espalhados pela aplicação.
 */
function configurarModalFiltro() {
  const modal = document.getElementById("modal-filtro");

  if (!modal) return;

  const closeBtn = modal.querySelector(".close-modal");
  const slider = document.getElementById("filter-rating");
  const ratingValue = document.getElementById("rating-value");
  const btnApply = document.getElementById("apply-filters");

  if (closeBtn) {
    closeBtn.onclick = () => {
      modal.style.display = "none";
    };
  }

  window.addEventListener("click", (event) => {
    if (event.target === modal) {
      modal.style.display = "none";
    }
  });

  if (slider && ratingValue) {
    slider.oninput = function () {
      ratingValue.innerText = this.value;
    };
  }

  if (btnApply) {
    btnApply.onclick = () => {
      const ano = document.getElementById("filter-year")?.value;
      const rating = slider?.value || 0;

      let url = `filterMedia.html?minRating=${rating}`;

      if (ano) {
        url += `&year=${ano}`;
      }

      window.location.href = url;
    };
  }
}

/* =========================================================
   CARREGAMENTO GENÉRICO DE MÍDIAS
   ========================================================= */

/**
 * Carrega cards de mídia em um grid específico usando o endpoint informado.
 * O comportamento de clique é mantido centralizado para evitar duplicação.
 *
 * @param {string} endpoint - Endpoint da API sem a barra inicial.
 * @param {string} gridId - ID do container onde as mídias serão renderizadas.
 */
async function carregarMidiasBase(endpoint, gridId) {
  const grid = document.getElementById(gridId);

  if (!grid) return;

  try {
    const response = await fetch(
      `${API_BASE_URL}/media/${endpoint}?page=0&size=10`,
    );

    if (!response.ok) {
      throw new Error(`Erro HTTP: ${response.status}`);
    }

    const data = await response.json();
    const lista = Array.isArray(data.content) ? data.content : data;

    grid.innerHTML = "";

    if (!Array.isArray(lista) || lista.length === 0) {
      grid.innerHTML =
        "<p style='color: var(--color4);'>Nenhum item encontrado.</p>";
      return;
    }

    lista.forEach((item) => {
      const img = document.createElement("img");

      img.src = item.image;
      img.alt = item.translatedTitle || item.title || "Mídia";
      img.className = "img-card";

      img.onclick = () => {
        const tipo = (item.type || endpoint).toLowerCase();
        window.location.href = `infoMedia.html?id=${item.id}&type=${tipo}`;
      };

      img.onerror = () => {
        img.src = PLACEHOLDER_IMAGE;
      };

      grid.appendChild(img);
    });
  } catch (error) {
    console.error(`Erro ao carregar ${endpoint}:`, error);
  }
}

/**
 * Função genérica para carregar qualquer lista de mídia.
 * Mantida para compatibilidade com chamadas já existentes no projeto.
 *
 * @param {string} tipo - Endpoint da API (ex: anime, filme, hq, manga).
 * @param {string} gridId - ID do container onde os itens serão renderizados.
 */
async function carregarMidia(tipo, gridId) {
  const grid = document.getElementById(gridId);

  if (!grid) return;

  grid.innerHTML = "<p style='color: white;'>Carregando...</p>";

  const URL = `${API_BASE_URL}/${tipo}/lista?size=100`;

  try {
    const response = await fetch(URL);

    if (!response.ok) {
      throw new Error(`Erro HTTP: ${response.status}`);
    }

    const data = await response.json();
    const lista = Array.isArray(data.content) ? data.content : data;

    grid.innerHTML = "";

    if (!Array.isArray(lista) || lista.length === 0) {
      grid.innerHTML =
        "<p style='color: var(--color4);'>Nenhum item encontrado.</p>";
      return;
    }

    lista.forEach((item) => {
      const img = document.createElement("img");

      img.src = item.image;
      img.alt = item.translatedTitle || item.title || "Mídia";
      img.className = "img-media";

      img.onclick = () => {
        const tipoUrl = (item.type || tipo).toLowerCase();
        window.location.href = `infoMedia.html?id=${item.id}&type=${tipoUrl}`;
      };

      img.onerror = () => {
        img.src = PLACEHOLDER_IMAGE;
      };

      grid.appendChild(img);
    });
  } catch (error) {
    console.error(`Erro ao carregar ${tipo}:`, error);

    grid.innerHTML =
      "<p style='color: red;'>Erro ao conectar com o servidor.</p>";
  }
}

/* =========================================================
   BANNER / CARROSSEL
   ========================================================= */

/**
 * Carrega as notícias que serão exibidas no banner principal.
 * O limite reduzido evita sobrecarga visual e melhora a rotação.
 */
async function inicializarBanner() {
  const bannerContainer = document.getElementById("img-banner");

  if (!bannerContainer) return;

  try {
    const response = await fetch(`${API_BASE_URL}/noticia/lista`);

    if (!response.ok) {
      throw new Error(`Erro HTTP: ${response.status}`);
    }

    const data = await response.json();
    noticiasBanner = (data.content || data || []).slice(0, 4);

    if (noticiasBanner.length > 0) {
      exibirNoticia(0);
      iniciarAutoPlay();
    }
  } catch (error) {
    console.error("Erro no banner:", error);
  }
}

/**
 * Atualiza o conteúdo visual do banner com base no índice recebido.
 *
 * @param {number} index - Índice da notícia a ser exibida.
 */
function exibirNoticia(index) {
  const img = document.getElementById("img-banner");
  const titulo = document.getElementById("titulo-banner");
  const link = document.getElementById("link-banner");
  const radios = document.getElementsByName("fixado");

  const noticia = noticiasBanner[index];

  if (!img || !titulo || !link || !noticia) return;

  indiceAtual = index;
  img.src = noticia.image;
  titulo.innerText = noticia.title;
  link.href = `newsDetail.html?id=${noticia.id}`;

  if (radios[index]) {
    radios[index].checked = true;
  }
}

/**
 * Inicia a troca automática das notícias exibidas no banner.
 * O intervalo é reiniciado quando a interação manual acontece.
 */
function iniciarAutoPlay() {
  clearInterval(intervaloBanner);

  if (!noticiasBanner.length) return;

  intervaloBanner = setInterval(() => {
    indiceAtual = (indiceAtual + 1) % noticiasBanner.length;
    exibirNoticia(indiceAtual);
  }, BANNER_INTERVAL_MS);
}

/**
 * Altera a notícia do banner manualmente através dos botões radio.
 *
 * @param {number} index - Índice da notícia selecionada.
 */
function mudarNoticiaManual(index) {
  clearInterval(intervaloBanner);
  exibirNoticia(index);
  iniciarAutoPlay();
}

/* =========================================================
   INICIALIZAÇÃO DA PÁGINA
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {
  incluirComponentes();
  inicializarBanner();
  carregarMidiasBase("lista-recentes", "lista-lancamentos-grid");
  carregarMidiasBase("lista-populares", "lista-populares-grid");
});
