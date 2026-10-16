# Checklist - Integração State + Observer

- [x] `Pedido` mantém exatamente um `EstadoPedido` atual.
- [x] Operações públicas delegam para o estado atual.
- [x] Cada estado concreto valida a operação e devolve o próximo estado.
- [x] Transições inválidas lançam `TransicaoInvalidaException`.
- [x] A troca de estado acontece somente em `Pedido.aplicarTransicao`.
- [x] `Pedido` registra e remove objetos `ObservadorPedido`.
- [x] `EventoPedido` é imutável e contém ação, estado anterior e estado novo.
- [x] A notificação acontece somente depois da troca efetiva.
- [x] Estados concretos não conhecem observadores.
- [x] Transições inválidas não produzem eventos nem notificações.
- [x] Há observadores distintos para cliente, auditoria e painel.
- [x] A API oferece criação, consulta, pagamento, envio e cancelamento de pedidos.
- [x] Testes cobrem transições válidas, recusas, histórico e remoção de observador.

## Sequência da integração

`State decide -> Pedido efetiva -> Observer divulga`

Transições permitidas:

| Estado atual | pagar | enviar | cancelar |
|---|---|---|---|
| `AGUARDANDO_PAGAMENTO` | `PAGO` | recusado | `CANCELADO` |
| `PAGO` | recusado | `ENVIADO` | `CANCELADO` |
| `ENVIADO` | recusado | recusado | recusado |
| `CANCELADO` | recusado | recusado | recusado |
