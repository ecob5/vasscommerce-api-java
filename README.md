# API do VassCommerce - Java

## Sobre o trabalho

Este projeto foi desenvolvido para a atividade da disciplina Laboratório de Programação FullStack. O objetivo foi criar, em Java, as rotas de consulta do e-commerce apresentado no diagrama de classes.

Utilizei Java 17 com Spring Boot. Os dados de exemplo ficam em memória para facilitar a execução e a correção da atividade, sem exigir a configuração de um banco de dados.

## Como executar

Requisitos:

- Java 17 ou mais recente;
- Maven 3.9 ou uma IDE com suporte ao Maven, como IntelliJ IDEA, Eclipse ou VS Code.

No terminal, dentro da pasta do projeto, execute:

```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

Para executar os testes:

```bash
mvn test
```

Também é possível abrir o projeto na IDE e executar a classe `VassCommerceApplication`.

## Rotas implementadas

| Método | Rota | Função |
|---|---|---|
| GET | `/categoria` | Lista todas as categorias |
| GET | `/categoria?nome=Informática` | Busca categorias pelo nome |
| GET | `/categoria/{idcategoria}/produto` | Lista os produtos de uma categoria |
| GET | `/produto/{id}` | Mostra os dados de um produto |
| GET | `/produto?nome=mouse&valorMinimo=10&valorMaximo=100` | Busca produtos com filtros opcionais |
| POST | `/produto` | Cria um produto validado |
| GET | `/cliente/{id}` | Mostra os dados de um cliente |
| GET | `/cliente/{idcliente}/formas-de-pagamento` | Lista os cartões ativos do cliente |
| GET | `/cliente/{idcliente}/endereco` | Mostra o endereço do cliente |
| GET | `/tipo-cartao` | Lista os tipos de cartão |
| GET | `/estado` | Lista os estados |
| GET | `/estado/{idestado}/cidade` | Lista as cidades de um estado |
| GET | `/cliente/{idcliente}/pedido` | Lista os pedidos de um cliente |
| POST | `/pedido` | Cria um pedido em `AGUARDANDO_PAGAMENTO` |
| GET | `/pedido/{id}` | Consulta estado, painel, histórico e notificações |
| POST | `/pedido/{id}/pagar` | Transição para `PAGO` |
| POST | `/pedido/{id}/enviar` | Transição de `PAGO` para `ENVIADO` |
| POST | `/pedido/{id}/cancelar` | Cancela quando o estado atual permite |

## Exemplos de consulta

```bash
curl "http://localhost:8080/categoria?nome=Informática"
curl http://localhost:8080/produto/1
curl http://localhost:8080/cliente/1/pedido
```

Exemplo de criação:

```bash
curl -i -X POST http://localhost:8080/produto \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teclado mecânico","descricao":"Teclado USB","valorUnitario":249.90,"categoriaId":1}'
```

O `POST /produto` responde `201 Created` e informa a URL do novo recurso no header `Location`.
Campos inválidos respondem `400 Bad Request` no formato `{"erro":"..."}`.

## IoC/DI

O `ProdutoController` depende somente da interface `ProdutoModel`, injetada por construtor.
Há duas implementações Spring: `ProdutoModelMemoria` e `ProdutoModelSql`. A implementação
selecionada atualmente é `produtoModelMemoria`, por `@Qualifier`. A implementação SQL é
simulada e delega ao armazenamento em memória enquanto o projeto não utiliza um banco real.

O checklist da etapa está em [`CHECKLIST.md`](CHECKLIST.md).

## Integração State + Observer

O ciclo de vida de um pedido usa o padrão **State** para concentrar as regras de transição nas
classes `AguardandoPagamento`, `Pago`, `Enviado` e `Cancelado`. O contexto `Pedido` delega a ação
ao estado atual e efetiva a troca em um único método.

O mesmo `Pedido` é o sujeito do padrão **Observer**. Depois da troca efetiva, ele publica um
`EventoPedido` imutável para três observadores independentes:

- `NotificadorCliente`: registra a mensagem destinada ao cliente;
- `AuditoriaPedido`: mantém o histórico das transições;
- `PainelAcompanhamento`: atualiza a situação exibida.

Transições recusadas retornam `409 Conflict`, não alteram o estado e não geram notificação.

Exemplo completo:

```bash
# cria o pedido e copie o id retornado
curl -i -X POST http://localhost:8080/pedido

curl -X POST http://localhost:8080/pedido/1001/pagar
curl -X POST http://localhost:8080/pedido/1001/enviar
curl http://localhost:8080/pedido/1001
```

O checklist específico está em [`CHECKLIST_STATE_OBSERVER.md`](CHECKLIST_STATE_OBSERVER.md).

## Observações

- Todas as respostas são enviadas em JSON.
- IDs inválidos retornam o status HTTP 400.
- Cadastros que não existem retornam o status HTTP 404.
- O campo `precoAtual` usa o menor preço entre as promoções vigentes.
- Um pedido pode ter pagamentos realizados com mais de um cartão.
- Somente os quatro últimos dígitos dos cartões aparecem nas respostas.
