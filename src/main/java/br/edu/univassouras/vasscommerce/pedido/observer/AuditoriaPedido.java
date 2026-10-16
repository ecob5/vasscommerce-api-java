package br.edu.univassouras.vasscommerce.pedido.observer;

import java.util.ArrayList;
import java.util.List;

public final class AuditoriaPedido implements ObservadorPedido {

    private final List<String> historico = new ArrayList<>();

    @Override
    public void aoMudarEstado(EventoPedido evento) {
        historico.add(evento.idPedido() + ": " + evento.estadoAnterior()
                + " --" + evento.acao() + "--> " + evento.estadoNovo());
    }

    public List<String> getHistorico() {
        return List.copyOf(historico);
    }
}
