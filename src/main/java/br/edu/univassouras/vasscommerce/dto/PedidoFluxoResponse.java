package br.edu.univassouras.vasscommerce.dto;

import java.util.List;

public record PedidoFluxoResponse(
        String id,
        String estado,
        String situacaoPainel,
        List<String> historico,
        List<String> notificacoes) {
}
