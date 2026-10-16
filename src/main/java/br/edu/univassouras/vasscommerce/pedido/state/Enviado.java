package br.edu.univassouras.vasscommerce.pedido.state;

import br.edu.univassouras.vasscommerce.pedido.Pedido;

public final class Enviado implements EstadoPedido {

    @Override
    public EstadoPedido pagar(Pedido pedido) {
        throw new TransicaoInvalidaException("o pedido já foi pago e enviado");
    }

    @Override
    public EstadoPedido enviar(Pedido pedido) {
        throw new TransicaoInvalidaException("o pedido já foi enviado");
    }

    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        throw new TransicaoInvalidaException("pedido em trânsito: use o fluxo de devolução");
    }

    @Override
    public String getNome() {
        return "ENVIADO";
    }
}
