package br.edu.univassouras.vasscommerce.pedido.state;

import br.edu.univassouras.vasscommerce.pedido.Pedido;

/** State: valida a operação e devolve o próximo estado. */
public interface EstadoPedido {

    EstadoPedido pagar(Pedido pedido);

    EstadoPedido enviar(Pedido pedido);

    EstadoPedido cancelar(Pedido pedido);

    String getNome();
}
