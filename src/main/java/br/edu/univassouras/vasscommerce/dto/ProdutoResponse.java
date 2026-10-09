package br.edu.univassouras.vasscommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
        Integer id,
        String nome,
        String descricao,
        String fotoUrl,
        LocalDateTime dataCadastro,
        LocalDateTime dataUltimaAtualizacao,
        BigDecimal valorUnitario,
        BigDecimal precoAtual,
        Integer categoriaId) {
}
