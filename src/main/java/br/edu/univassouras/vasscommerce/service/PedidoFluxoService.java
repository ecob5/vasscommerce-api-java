package br.edu.univassouras.vasscommerce.service;

import br.edu.univassouras.vasscommerce.dto.PedidoFluxoResponse;
import br.edu.univassouras.vasscommerce.exception.CadastroNaoEncontradoException;
import br.edu.univassouras.vasscommerce.pedido.Pedido;
import br.edu.univassouras.vasscommerce.pedido.observer.AuditoriaPedido;
import br.edu.univassouras.vasscommerce.pedido.observer.NotificadorCliente;
import br.edu.univassouras.vasscommerce.pedido.observer.PainelAcompanhamento;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PedidoFluxoService {

    private final AtomicInteger sequencia = new AtomicInteger(1000);
    private final Map<String, PedidoAcompanhado> pedidos = new ConcurrentHashMap<>();

    public PedidoFluxoResponse criar() {
        String id = String.valueOf(sequencia.incrementAndGet());
        Pedido pedido = new Pedido(id);
        AuditoriaPedido auditoria = new AuditoriaPedido();
        NotificadorCliente notificador = new NotificadorCliente();
        PainelAcompanhamento painel = new PainelAcompanhamento();

        pedido.registrarObservador(notificador);
        pedido.registrarObservador(auditoria);
        pedido.registrarObservador(painel);

        PedidoAcompanhado acompanhado = new PedidoAcompanhado(pedido, auditoria, notificador, painel);
        pedidos.put(id, acompanhado);
        return toResponse(acompanhado);
    }

    public PedidoFluxoResponse obter(String id) {
        return toResponse(buscar(id));
    }

    public PedidoFluxoResponse pagar(String id) {
        PedidoAcompanhado acompanhado = buscar(id);
        acompanhado.pedido().pagar();
        return toResponse(acompanhado);
    }

    public PedidoFluxoResponse enviar(String id) {
        PedidoAcompanhado acompanhado = buscar(id);
        acompanhado.pedido().enviar();
        return toResponse(acompanhado);
    }

    public PedidoFluxoResponse cancelar(String id) {
        PedidoAcompanhado acompanhado = buscar(id);
        acompanhado.pedido().cancelar();
        return toResponse(acompanhado);
    }

    private PedidoAcompanhado buscar(String id) {
        PedidoAcompanhado acompanhado = pedidos.get(id);
        if (acompanhado == null) {
            throw new CadastroNaoEncontradoException("Pedido " + id + " não encontrado.");
        }
        return acompanhado;
    }

    private PedidoFluxoResponse toResponse(PedidoAcompanhado acompanhado) {
        return new PedidoFluxoResponse(
                acompanhado.pedido().getId(),
                acompanhado.pedido().getNomeEstado(),
                acompanhado.painel().getSituacaoExibida(),
                acompanhado.auditoria().getHistorico(),
                acompanhado.notificador().getNotificacoes());
    }

    private record PedidoAcompanhado(
            Pedido pedido,
            AuditoriaPedido auditoria,
            NotificadorCliente notificador,
            PainelAcompanhamento painel) {
    }
}
