package br.edu.univassouras.vasscommerce;

import br.edu.univassouras.vasscommerce.pedido.Pedido;
import br.edu.univassouras.vasscommerce.pedido.observer.AuditoriaPedido;
import br.edu.univassouras.vasscommerce.pedido.observer.PainelAcompanhamento;
import br.edu.univassouras.vasscommerce.pedido.state.TransicaoInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PedidoStateObserverTests {

    @Test
    void deveNotificarSomenteDepoisDeTransicoesEfetivas() {
        Pedido pedido = new Pedido("1001");
        AuditoriaPedido auditoria = new AuditoriaPedido();
        PainelAcompanhamento painel = new PainelAcompanhamento();
        pedido.registrarObservador(auditoria);
        pedido.registrarObservador(painel);

        assertThrows(TransicaoInvalidaException.class, pedido::enviar);
        assertEquals("AGUARDANDO_PAGAMENTO", pedido.getNomeEstado());
        assertEquals(0, auditoria.getHistorico().size());

        pedido.pagar();
        assertEquals("PAGO", pedido.getNomeEstado());
        assertEquals("PAGO", painel.getSituacaoExibida());
        assertEquals(1, auditoria.getHistorico().size());

        pedido.enviar();
        assertEquals("ENVIADO", pedido.getNomeEstado());
        assertEquals(2, auditoria.getHistorico().size());

        assertThrows(TransicaoInvalidaException.class, pedido::cancelar);
        assertEquals(2, auditoria.getHistorico().size());
    }

    @Test
    void devePermitirRemoverObservador() {
        Pedido pedido = new Pedido("1002");
        AuditoriaPedido auditoria = new AuditoriaPedido();
        pedido.registrarObservador(auditoria);
        pedido.removerObservador(auditoria);

        pedido.pagar();

        assertEquals(0, auditoria.getHistorico().size());
    }
}
