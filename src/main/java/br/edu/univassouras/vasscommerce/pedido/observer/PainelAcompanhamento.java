package br.edu.univassouras.vasscommerce.pedido.observer;

public final class PainelAcompanhamento implements ObservadorPedido {

    private String situacaoExibida = "AGUARDANDO_PAGAMENTO";

    @Override
    public void aoMudarEstado(EventoPedido evento) {
        situacaoExibida = evento.estadoNovo();
    }

    public String getSituacaoExibida() {
        return situacaoExibida;
    }
}
