package br.edu.univassouras.vasscommerce.pedido.observer;

/** Evento imutável emitido somente depois de uma transição efetiva. */
public record EventoPedido(
        String idPedido,
        String acao,
        String estadoAnterior,
        String estadoNovo) {
}
