package br.edu.univassouras.vasscommerce.mapper;

import br.edu.univassouras.vasscommerce.dto.ProdutoCreateRequest;
import br.edu.univassouras.vasscommerce.dto.ProdutoResponse;
import br.edu.univassouras.vasscommerce.model.Dados.Produto;

import java.time.LocalDateTime;

public final class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static Produto toEntity(ProdutoCreateRequest request, Integer id) {
        LocalDateTime agora = LocalDateTime.now();
        return new Produto(id, request.nome(), request.descricao(), request.fotoUrl(),
                agora, agora, request.valorUnitario(), request.categoriaId());
    }

    public static ProdutoResponse toResponse(Produto produto, java.math.BigDecimal precoAtual) {
        return new ProdutoResponse(produto.id(), produto.nome(), produto.descricao(), produto.fotoUrl(),
                produto.dataCadastro(), produto.dataUltimaAtualizacao(), produto.valorUnitario(),
                precoAtual, produto.categoriaId());
    }
}
