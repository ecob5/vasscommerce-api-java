package br.edu.univassouras.vasscommerce.model;

import br.edu.univassouras.vasscommerce.dto.ProdutoCreateRequest;
import br.edu.univassouras.vasscommerce.mapper.ProdutoMapper;
import br.edu.univassouras.vasscommerce.model.Dados.Produto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service("produtoModelMemoria")
public class ProdutoModelMemoria implements ProdutoModel {

    private final List<Produto> banco = new ArrayList<>(List.of(
            new Produto(1, "Notebook Start", "Notebook com 8 GB de memória e SSD de 256 GB", null,
                    dataHora(2026, 8, 1, 10, 0), dataHora(2026, 8, 20, 14, 30), dinheiro("2499.90"), 1),
            new Produto(2, "Mouse sem fio", "Mouse óptico com conexão USB", null,
                    dataHora(2026, 8, 2, 9, 0), dataHora(2026, 8, 21, 11, 0), dinheiro("89.90"), 1),
            new Produto(3, "Java para iniciantes", "Introdução prática à programação em Java", null,
                    dataHora(2026, 8, 3, 12, 0), dataHora(2026, 8, 3, 12, 0), dinheiro("79.90"), 2),
            new Produto(4, "Mochila urbana", "Mochila resistente com compartimento para notebook", null,
                    dataHora(2026, 8, 5, 15, 0), dataHora(2026, 8, 26, 9, 15), dinheiro("159.90"), 3)
    ));

    private final AtomicInteger seq = new AtomicInteger(4);

    @Override
    public synchronized Produto criar(ProdutoCreateRequest request) {
        Produto produto = ProdutoMapper.toEntity(request, seq.incrementAndGet());
        banco.add(produto);
        return produto;
    }

    @Override
    public synchronized Optional<Produto> obterPorId(Integer id) {
        return banco.stream().filter(produto -> produto.id().equals(id)).findFirst();
    }

    @Override
    public synchronized List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo) {
        String termo = nome == null ? null : nome.trim().toLowerCase(Locale.ROOT);
        return banco.stream()
                .filter(produto -> termo == null || termo.isBlank()
                        || produto.nome().toLowerCase(Locale.ROOT).contains(termo))
                .filter(produto -> valorMinimo == null || produto.valorUnitario().compareTo(valorMinimo) >= 0)
                .filter(produto -> valorMaximo == null || produto.valorUnitario().compareTo(valorMaximo) <= 0)
                .toList();
    }

    @Override
    public BigDecimal precoAtual(Produto produto) {
        if (produto.id().equals(1)) {
            return dinheiro("2199.90");
        }
        if (produto.id().equals(2)) {
            return dinheiro("69.90");
        }
        return produto.valorUnitario();
    }

    private static BigDecimal dinheiro(String valor) {
        return new BigDecimal(valor);
    }

    private static LocalDateTime dataHora(int ano, int mes, int dia, int hora, int minuto) {
        return LocalDateTime.of(ano, mes, dia, hora, minuto);
    }
}
