package br.edu.univassouras.vasscommerce.pedido.state;

import br.edu.univassouras.vasscommerce.pedido.Pedido;

public final class Cancelado implements EstadoPedido {

    @Override
    public EstadoPedido pagar(Pedido pedido) {
        throw new TransicaoInvalidaException("pedido cancelado não aceita pagamento");
    }

    @Override
    public EstadoPedido enviar(Pedido pedido) {
        throw new TransicaoInvalidaException("pedido cancelado não pode ser enviado");
    }

    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        throw new TransicaoInvalidaException("o pedido já está cancelado");
    }

    @Override
    public String getNome() {
        return "CANCELADO";
    }
}
