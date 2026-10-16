package br.edu.univassouras.vasscommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VassCommerceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveListarCategorias() throws Exception {
        mockMvc.perform(get("/categoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void deveBuscarCategoriaPorNome() throws Exception {
        mockMvc.perform(get("/categoria").param("nome", "informática"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Informática"));
    }

    @Test
    void deveListarProdutosDaCategoria() throws Exception {
        mockMvc.perform(get("/categoria/1/produto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtos.length()").value(2));
    }

    @Test
    void deveUsarOMenorPrecoPromocional() throws Exception {
        mockMvc.perform(get("/produto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precoAtual").value(2199.90));
    }

    @Test
    void deveBuscarCliente() throws Exception {
        mockMvc.perform(get("/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCompleto").value("Ana Souza"));
    }

    @Test
    void deveListarFormasDePagamento() throws Exception {
        mockMvc.perform(get("/cliente/1/formas-de-pagamento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveBuscarEndereco() throws Exception {
        mockMvc.perform(get("/cliente/1/endereco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade.nome").value("Vassouras"))
                .andExpect(jsonPath("$.estado.sigla").value("RJ"));
    }

    @Test
    void deveListarTiposDeCartao() throws Exception {
        mockMvc.perform(get("/tipo-cartao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveListarEstadosECidades() throws Exception {
        mockMvc.perform(get("/estado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

        mockMvc.perform(get("/estado/1/cidade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidades.length()").value(2));
    }

    @Test
    void deveListarPedidosComMaisDeUmPagamento() throws Exception {
        mockMvc.perform(get("/cliente/1/pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].valorTotal").value(2339.70))
                .andExpect(jsonPath("$[0].pagamentos.length()").value(2));
    }

    @Test
    void deveResponderNotFound() throws Exception {
        mockMvc.perform(get("/produto/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("Produto não encontrado."));
    }

    @Test
    void deveRejeitarIdInvalido() throws Exception {
        mockMvc.perform(get("/produto/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveCriarProdutoComInjecaoDoModel() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/produto")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"nome":"Teclado mecânico","descricao":"Teclado USB",
                                "valorUnitario":249.90,"categoriaId":1}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/produto/")))
                .andExpect(jsonPath("$.nome").value("Teclado mecânico"));
    }

    @Test
    void devePadronizarErroDeValidacao() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/produto")
                        .contentType(APPLICATION_JSON)
                        .content("{\"nome\":\"\",\"descricao\":\"\",\"valorUnitario\":0,\"categoriaId\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").exists());
    }

    @Test
    void deveBuscarProdutosComFiltro() throws Exception {
        mockMvc.perform(get("/produto").param("nome", "mouse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Mouse sem fio"));
    }
}
