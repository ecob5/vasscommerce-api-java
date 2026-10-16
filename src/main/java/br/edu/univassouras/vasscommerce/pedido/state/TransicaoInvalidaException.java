package br.edu.univassouras.vasscommerce.pedido.state;

public class TransicaoInvalidaException extends RuntimeException {

    public TransicaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
