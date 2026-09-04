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
| GET | `/cliente/{id}` | Mostra os dados de um cliente |
| GET | `/cliente/{idcliente}/formas-de-pagamento` | Lista os cartões ativos do cliente |
| GET | `/cliente/{idcliente}/endereco` | Mostra o endereço do cliente |
| GET | `/tipo-cartao` | Lista os tipos de cartão |
| GET | `/estado` | Lista os estados |
| GET | `/estado/{idestado}/cidade` | Lista as cidades de um estado |
| GET | `/cliente/{idcliente}/pedido` | Lista os pedidos de um cliente |

## Exemplos de consulta

```bash
curl "http://localhost:8080/categoria?nome=Informática"
curl http://localhost:8080/produto/1
curl http://localhost:8080/cliente/1/pedido
```

## Observações

- Todas as respostas são enviadas em JSON.
- IDs inválidos retornam o status HTTP 400.
- Cadastros que não existem retornam o status HTTP 404.
- O campo `precoAtual` usa o menor preço entre as promoções vigentes.
- Um pedido pode ter pagamentos realizados com mais de um cartão.
- Somente os quatro últimos dígitos dos cartões aparecem nas respostas.
