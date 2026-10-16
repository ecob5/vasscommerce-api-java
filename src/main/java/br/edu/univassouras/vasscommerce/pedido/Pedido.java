package br.edu.univassouras.vasscommerce.pedido;

import br.edu.univassouras.vasscommerce.pedido.observer.EventoPedido;
import br.edu.univassouras.vasscommerce.pedido.observer.ObservadorPedido;
import br.edu.univassouras.vasscommerce.pedido.state.AguardandoPagamento;
import br.edu.univassouras.vasscommerce.pedido.state.EstadoPedido;

import java.util.ArrayList;
import java.util.List;

/** Contexto do State e sujeito do Observer. */
public final class Pedido {

    private final String id;
    private EstadoPedido estado;
    private final List<ObservadorPedido> observadores = new ArrayList<>();

    public Pedido(String id) {
        this.id = id;
        this.estado = new AguardandoPagamento();
    }

    public void registrarObservador(ObservadorPedido observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void removerObservador(ObservadorPedido observador) {
        observadores.remove(observador);
    }

    public void pagar() {
        aplicarTransicao("pagar", estado.pagar(this));
    }

    public void enviar() {
        aplicarTransicao("enviar", estado.enviar(this));
    }

    public void cancelar() {
        aplicarTransicao("cancelar", estado.cancelar(this));
    }

    private void aplicarTransicao(String acao, EstadoPedido novoEstado) {
        if (novoEstado == null || novoEstado == estado) {
            return;
        }
        EstadoPedido anterior = estado;
        estado = novoEstado;
        notificar(new EventoPedido(id, acao, anterior.getNome(), novoEstado.getNome()));
    }

    private void notificar(EventoPedido evento) {
        for (ObservadorPedido observador : List.copyOf(observadores)) {
            observador.aoMudarEstado(evento);
        }
    }

    public String getId() {
        return id;
    }

    public String getNomeEstado() {
        return estado.getNome();
    }
}
