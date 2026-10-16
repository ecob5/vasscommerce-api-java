package br.edu.univassouras.vasscommerce.pedido.state;

import br.edu.univassouras.vasscommerce.pedido.Pedido;

public final class AguardandoPagamento implements EstadoPedido {

    @Override
    public EstadoPedido pagar(Pedido pedido) {
        return new Pago();
    }

    @Override
    public EstadoPedido enviar(Pedido pedido) {
        throw new TransicaoInvalidaException("não é possível enviar antes do pagamento");
    }

    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        return new Cancelado();
    }

    @Override
    public String getNome() {
        return "AGUARDANDO_PAGAMENTO";
    }
}
