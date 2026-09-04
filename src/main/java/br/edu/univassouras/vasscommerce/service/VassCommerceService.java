package br.edu.univassouras.vasscommerce.service;

import br.edu.univassouras.vasscommerce.exception.CadastroNaoEncontradoException;
import br.edu.univassouras.vasscommerce.model.Dados.Cartao;
import br.edu.univassouras.vasscommerce.model.Dados.Categoria;
import br.edu.univassouras.vasscommerce.model.Dados.Cidade;
import br.edu.univassouras.vasscommerce.model.Dados.Cliente;
import br.edu.univassouras.vasscommerce.model.Dados.Endereco;
import br.edu.univassouras.vasscommerce.model.Dados.Estado;
import br.edu.univassouras.vasscommerce.model.Dados.ItemPedido;
import br.edu.univassouras.vasscommerce.model.Dados.Pagamento;
import br.edu.univassouras.vasscommerce.model.Dados.Pedido;
import br.edu.univassouras.vasscommerce.model.Dados.Produto;
import br.edu.univassouras.vasscommerce.model.Dados.Promocao;
import br.edu.univassouras.vasscommerce.model.Dados.PromocaoItem;
import br.edu.univassouras.vasscommerce.model.Dados.TipoCartao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class VassCommerceService {

    private final List<Categoria> categorias = List.of(
            new Categoria(1, "Informática", "Computadores e periféricos"),
            new Categoria(2, "Livros", "Livros técnicos e de literatura"),
            new Categoria(3, "Acessórios", "Acessórios para o dia a dia")
    );

    private final List<Produto> produtos = List.of(
            new Produto(1, "Notebook Start", "Notebook com 8 GB de memória e SSD de 256 GB",
                    "https://exemplo.com/imagens/notebook-start.jpg",
                    dataHora(2026, 8, 1, 10, 0), dataHora(2026, 8, 20, 14, 30),
                    dinheiro("2499.90"), 1),
            new Produto(2, "Mouse sem fio", "Mouse óptico com conexão USB",
                    "https://exemplo.com/imagens/mouse-sem-fio.jpg",
                    dataHora(2026, 8, 2, 9, 0), dataHora(2026, 8, 21, 11, 0),
                    dinheiro("89.90"), 1),
            new Produto(3, "Java para iniciantes", "Introdução prática à programação em Java",
                    "https://exemplo.com/imagens/livro-java.jpg",
                    dataHora(2026, 8, 3, 12, 0), dataHora(2026, 8, 3, 12, 0),
                    dinheiro("79.90"), 2),
            new Produto(4, "Mochila urbana", "Mochila resistente com compartimento para notebook",
                    "https://exemplo.com/imagens/mochila-urbana.jpg",
                    dataHora(2026, 8, 5, 15, 0), dataHora(2026, 8, 26, 9, 15),
                    dinheiro("159.90"), 3)
    );

    private final List<Promocao> promocoes = List.of(
            new Promocao(1, "Oferta especial", "Promoção de produtos selecionados",
                    LocalDate.of(2020, 1, 1), LocalDate.of(2030, 12, 31)),
            new Promocao(2, "Semana do cliente", "Descontos da semana do cliente",
                    LocalDate.of(2020, 1, 1), LocalDate.of(2030, 12, 31))
    );

    private final List<PromocaoItem> promocaoItens = List.of(
            new PromocaoItem(1, 1, dinheiro("2299.90")),
            new PromocaoItem(2, 1, dinheiro("2199.90")),
            new PromocaoItem(1, 2, dinheiro("69.90"))
    );

    private final List<Estado> estados = List.of(
            new Estado(1, "RJ", "Rio de Janeiro"),
            new Estado(2, "SP", "São Paulo"),
            new Estado(3, "MG", "Minas Gerais")
    );

    private final List<Cidade> cidades = List.of(
            new Cidade(1, "Vassouras", 1),
            new Cidade(2, "Rio de Janeiro", 1),
            new Cidade(3, "São Paulo", 2),
            new Cidade(4, "Campinas", 2),
            new Cidade(5, "Belo Horizonte", 3)
    );

    private final List<Cliente> clientes = List.of(
            new Cliente(1, "Ana Souza", "ana.souza@email.com", "123.456.789-00",
                    LocalDate.of(2001, 5, 18), "https://exemplo.com/imagens/ana.jpg",
                    dataHora(2026, 7, 10, 13, 0), dataHora(2026, 8, 10, 13, 0)),
            new Cliente(2, "Bruno Lima", "bruno.lima@email.com", "987.654.321-00",
                    LocalDate.of(1998, 11, 2), "https://exemplo.com/imagens/bruno.jpg",
                    dataHora(2026, 7, 15, 10, 30), dataHora(2026, 7, 15, 10, 30))
    );

    private final List<Endereco> enderecos = List.of(
            new Endereco(1, 1, "Rua das Flores", 120, "27700-000", "Apartamento 201",
                    "(24) 99999-1111", "Centro", 1),
            new Endereco(2, 2, "Avenida Paulista", 900, "01310-100", null,
                    "(11) 98888-2222", "Bela Vista", 3)
    );

    private final List<TipoCartao> tiposCartao = List.of(
            new TipoCartao(1, "DÉBITO"),
            new TipoCartao(2, "CRÉDITO")
    );

    private final List<Cartao> cartoes = List.of(
            new Cartao(1, 1, 2, "4821", dataHora(2026, 7, 10, 13, 10), false),
            new Cartao(2, 1, 1, "7734", dataHora(2026, 7, 11, 9, 0), false),
            new Cartao(3, 2, 2, "1098", dataHora(2026, 7, 15, 10, 40), false)
    );

    private final List<Pedido> pedidos = List.of(
            new Pedido(1, 1, dataHora(2026, 9, 3, 16, 0), "ENTREGUE_TRANSPORTADORA",
                    List.of(
                            new ItemPedido(1, 1, dinheiro("2199.90")),
                            new ItemPedido(2, 2, dinheiro("69.90"))
                    ),
                    List.of(
                            new Pagamento(1, dinheiro("1500.00")),
                            new Pagamento(2, dinheiro("839.70"))
                    )),
            new Pedido(2, 1, dataHora(2026, 8, 20, 11, 30), "ENTREGUE_CLIENTE",
                    List.of(new ItemPedido(3, 1, dinheiro("79.90"))),
                    List.of(new Pagamento(1, dinheiro("79.90")))),
            new Pedido(3, 2, dataHora(2026, 9, 4, 8, 20), "AGUARDANDO_PAGAMENTO",
                    List.of(new ItemPedido(4, 1, dinheiro("159.90"))), List.of())
    );

    public List<Categoria> listarCategorias(String nome) {
        if (nome == null || nome.isBlank()) {
            return categorias;
        }

        String termo = nome.trim().toLowerCase(Locale.ROOT);
        return categorias.stream()
                .filter(categoria -> categoria.nome().toLowerCase(Locale.ROOT).contains(termo))
                .toList();
    }

    public Map<String, Object> listarProdutosDaCategoria(int categoriaId) {
        validarId(categoriaId, "categoria");
        Categoria categoria = buscarCategoria(categoriaId);
        List<Map<String, Object>> produtosDaCategoria = produtos.stream()
                .filter(produto -> produto.categoriaId().equals(categoriaId))
                .map(this::montarProduto)
                .toList();

        return Map.of("categoria", categoria, "produtos", produtosDaCategoria);
    }

    public Map<String, Object> buscarProduto(int produtoId) {
        validarId(produtoId, "produto");
        Produto produto = produtos.stream()
                .filter(item -> item.id().equals(produtoId))
                .findFirst()
                .orElseThrow(() -> new CadastroNaoEncontradoException("Produto não encontrado."));
        return montarProduto(produto);
    }

    public Cliente buscarCliente(int clienteId) {
        validarId(clienteId, "cliente");
        return clientes.stream()
                .filter(cliente -> cliente.id().equals(clienteId))
                .findFirst()
                .orElseThrow(() -> new CadastroNaoEncontradoException("Cliente não encontrado."));
    }

    public List<Map<String, Object>> listarFormasDePagamento(int clienteId) {
        buscarCliente(clienteId);
        return cartoes.stream()
                .filter(cartao -> cartao.clienteId().equals(clienteId) && !cartao.excluido())
                .map(cartao -> {
                    TipoCartao tipo = tiposCartao.stream()
                            .filter(item -> item.id().equals(cartao.tipoCartaoId()))
                            .findFirst().orElse(null);
                    Map<String, Object> resposta = new LinkedHashMap<>();
                    resposta.put("id", cartao.id());
                    resposta.put("final", cartao.numeroFinal());
                    resposta.put("dataCriacao", cartao.dataCriacao());
                    resposta.put("tipo", tipo == null ? null : tipo.nome());
                    return resposta;
                })
                .toList();
    }

    public Map<String, Object> buscarEndereco(int clienteId) {
        buscarCliente(clienteId);
        Endereco endereco = enderecos.stream()
                .filter(item -> item.clienteId().equals(clienteId))
                .findFirst()
                .orElseThrow(() -> new CadastroNaoEncontradoException("Endereço não encontrado."));
        Cidade cidade = cidades.stream()
                .filter(item -> item.id().equals(endereco.cidadeId()))
                .findFirst().orElse(null);
        Estado estado = cidade == null ? null : estados.stream()
                .filter(item -> item.id().equals(cidade.estadoId()))
                .findFirst().orElse(null);

        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("id", endereco.id());
        resposta.put("rua", endereco.rua());
        resposta.put("numero", endereco.numero());
        resposta.put("cep", endereco.cep());
        resposta.put("complemento", endereco.complemento());
        resposta.put("telefone", endereco.telefone());
        resposta.put("bairro", endereco.bairro());
        resposta.put("cidade", cidade);
        resposta.put("estado", estado);
        return resposta;
    }

    public List<TipoCartao> listarTiposCartao() {
        return tiposCartao;
    }

    public List<Estado> listarEstados() {
        return estados;
    }

    public Map<String, Object> listarCidadesDoEstado(int estadoId) {
        validarId(estadoId, "estado");
        Estado estado = estados.stream()
                .filter(item -> item.id().equals(estadoId))
                .findFirst()
                .orElseThrow(() -> new CadastroNaoEncontradoException("Estado não encontrado."));
        List<Cidade> cidadesDoEstado = cidades.stream()
                .filter(cidade -> cidade.estadoId().equals(estadoId))
                .toList();
        return Map.of("estado", estado, "cidades", cidadesDoEstado);
    }

    public List<Map<String, Object>> listarPedidosDoCliente(int clienteId) {
        buscarCliente(clienteId);
        return pedidos.stream()
                .filter(pedido -> pedido.clienteId().equals(clienteId))
                .map(this::montarPedido)
                .toList();
    }

    private Categoria buscarCategoria(int categoriaId) {
        return categorias.stream()
                .filter(categoria -> categoria.id().equals(categoriaId))
                .findFirst()
                .orElseThrow(() -> new CadastroNaoEncontradoException("Categoria não encontrada."));
    }

    private Map<String, Object> montarProduto(Produto produto) {
        Categoria categoria = buscarCategoria(produto.categoriaId());
        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("id", produto.id());
        resposta.put("nome", produto.nome());
        resposta.put("descricao", produto.descricao());
        resposta.put("fotoUrl", produto.fotoUrl());
        resposta.put("dataCadastro", produto.dataCadastro());
        resposta.put("dataUltimaAtualizacao", produto.dataUltimaAtualizacao());
        resposta.put("valorUnitario", produto.valorUnitario());
        resposta.put("categoria", categoria);
        resposta.put("precoAtual", calcularPrecoAtual(produto));
        return resposta;
    }

    private BigDecimal calcularPrecoAtual(Produto produto) {
        LocalDate hoje = LocalDate.now();
        return promocaoItens.stream()
                .filter(item -> item.produtoId().equals(produto.id()))
                .filter(item -> promocoes.stream().anyMatch(promocao ->
                        promocao.id().equals(item.promocaoId())
                                && !hoje.isBefore(promocao.dataInicio())
                                && !hoje.isAfter(promocao.dataFim())))
                .map(PromocaoItem::valorPromocao)
                .min(BigDecimal::compareTo)
                .map(valorPromocional -> valorPromocional.min(produto.valorUnitario()))
                .orElse(produto.valorUnitario());
    }

    private Map<String, Object> montarPedido(Pedido pedido) {
        List<Map<String, Object>> itens = pedido.itens().stream().map(item -> {
            Produto produto = produtos.stream()
                    .filter(p -> p.id().equals(item.produtoId()))
                    .findFirst().orElse(null);
            Map<String, Object> resposta = new LinkedHashMap<>();
            resposta.put("produtoId", item.produtoId());
            resposta.put("nomeProduto", produto == null ? null : produto.nome());
            resposta.put("quantidade", item.quantidade());
            resposta.put("valorUnitario", item.valorUnitario());
            resposta.put("subtotal", item.valorUnitario().multiply(BigDecimal.valueOf(item.quantidade())));
            return resposta;
        }).toList();

        BigDecimal valorTotal = itens.stream()
                .map(item -> (BigDecimal) item.get("subtotal"))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Map<String, Object>> pagamentos = pedido.pagamentos().stream().map(pagamento -> {
            Cartao cartao = cartoes.stream()
                    .filter(item -> item.id().equals(pagamento.cartaoId()))
                    .findFirst().orElse(null);
            Map<String, Object> resposta = new LinkedHashMap<>();
            resposta.put("cartaoId", pagamento.cartaoId());
            resposta.put("finalCartao", cartao == null ? null : cartao.numeroFinal());
            resposta.put("valorPago", pagamento.valorPago());
            return resposta;
        }).toList();

        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("id", pedido.id());
        resposta.put("dataCadastro", pedido.dataCadastro());
        resposta.put("status", pedido.status());
        resposta.put("valorTotal", valorTotal);
        resposta.put("itens", itens);
        resposta.put("pagamentos", pagamentos);
        return resposta;
    }

    private void validarId(int id, String tipo) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id de " + tipo + " deve ser um número inteiro positivo.");
        }
    }

    private static BigDecimal dinheiro(String valor) {
        return new BigDecimal(valor);
    }

    private static LocalDateTime dataHora(int ano, int mes, int dia, int hora, int minuto) {
        return LocalDateTime.of(ano, mes, dia, hora, minuto);
    }
}
