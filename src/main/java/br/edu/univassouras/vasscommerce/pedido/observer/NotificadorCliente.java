package br.edu.univassouras.vasscommerce.pedido.observer;

import java.util.ArrayList;
import java.util.List;

public final class NotificadorCliente implements ObservadorPedido {

    private final List<String> notificacoes = new ArrayList<>();

    @Override
    public void aoMudarEstado(EventoPedido evento) {
        String texto = switch (evento.estadoNovo()) {
            case "PAGO" -> "recebemos seu pagamento";
            case "ENVIADO" -> "seu pedido saiu para entrega";
            case "CANCELADO" -> "seu pedido foi cancelado";
            default -> null;
        };
        if (texto != null) {
            notificacoes.add("Pedido " + evento.idPedido() + ": " + texto + ".");
        }
    }

    public List<String> getNotificacoes() {
        return List.copyOf(notificacoes);
    }
}
