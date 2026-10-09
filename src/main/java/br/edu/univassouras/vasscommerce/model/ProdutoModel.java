package br.edu.univassouras.vasscommerce.model;

import br.edu.univassouras.vasscommerce.dto.ProdutoCreateRequest;
import br.edu.univassouras.vasscommerce.model.Dados.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoModel {

    Produto criar(ProdutoCreateRequest request);

    Optional<Produto> obterPorId(Integer id);

    List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo);

    BigDecimal precoAtual(Produto produto);
}
