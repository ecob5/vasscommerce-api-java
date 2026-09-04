package br.edu.univassouras.vasscommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class Dados {

    private Dados() {
    }

    public record Categoria(Integer id, String nome, String descricao) {
    }

    public record Produto(
            Integer id,
            String nome,
            String descricao,
            String fotoUrl,
            LocalDateTime dataCadastro,
            LocalDateTime dataUltimaAtualizacao,
            BigDecimal valorUnitario,
            Integer categoriaId) {
    }

    public record Promocao(
            Integer id,
            String nome,
            String descricao,
            LocalDate dataInicio,
            LocalDate dataFim) {
    }

    public record PromocaoItem(Integer promocaoId, Integer produtoId, BigDecimal valorPromocao) {
    }

    public record Cliente(
            Integer id,
            String nomeCompleto,
            String email,
            String cpf,
            LocalDate dataNascimento,
            String fotoUrl,
            LocalDateTime dataCadastro,
            LocalDateTime dataUltimaAtualizacao) {
    }

    public record Estado(Integer id, String sigla, String nome) {
    }

    public record Cidade(Integer id, String nome, Integer estadoId) {
    }

    public record Endereco(
            Integer id,
            Integer clienteId,
            String rua,
            Integer numero,
            String cep,
            String complemento,
            String telefone,
            String bairro,
            Integer cidadeId) {
    }

    public record TipoCartao(Integer id, String nome) {
    }

    public record Cartao(
            Integer id,
            Integer clienteId,
            Integer tipoCartaoId,
            String numeroFinal,
            LocalDateTime dataCriacao,
            boolean excluido) {
    }

    public record ItemPedido(Integer produtoId, Integer quantidade, BigDecimal valorUnitario) {
    }

    public record Pagamento(Integer cartaoId, BigDecimal valorPago) {
    }

    public record Pedido(
            Integer id,
            Integer clienteId,
            LocalDateTime dataCadastro,
            String status,
            List<ItemPedido> itens,
            List<Pagamento> pagamentos) {
    }
}
