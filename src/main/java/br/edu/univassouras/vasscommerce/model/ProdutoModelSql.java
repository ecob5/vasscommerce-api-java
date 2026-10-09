package br.edu.univassouras.vasscommerce.model;

import br.edu.univassouras.vasscommerce.dto.ProdutoCreateRequest;
import br.edu.univassouras.vasscommerce.model.Dados.Produto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/** Implementação que representa um backend SQL; o armazenamento é simulado nesta etapa. */
@Service("produtoModelSql")
public class ProdutoModelSql implements ProdutoModel {

    private final ProdutoModelMemoria memoria;

    public ProdutoModelSql(ProdutoModelMemoria memoria) {
        this.memoria = memoria;
    }

    @Override
    public Produto criar(ProdutoCreateRequest request) {
        return memoria.criar(request);
    }

    @Override
    public Optional<Produto> obterPorId(Integer id) {
        return memoria.obterPorId(id);
    }

    @Override
    public List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return memoria.buscar(nome, valorMinimo, valorMaximo);
    }

    @Override
    public BigDecimal precoAtual(Produto produto) {
        return memoria.precoAtual(produto);
    }
}
