package br.edu.univassouras.vasscommerce.pedido.state;

import br.edu.univassouras.vasscommerce.pedido.Pedido;

public final class Pago implements EstadoPedido {

    @Override
    public EstadoPedido pagar(Pedido pedido) {
        throw new TransicaoInvalidaException("o pedido já foi pago");
    }

    @Override
    public EstadoPedido enviar(Pedido pedido) {
        return new Enviado();
    }

    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        return new Cancelado();
    }

    @Override
    public String getNome() {
        return "PAGO";
    }
}
