package br.edu.univassouras.vasscommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoCreateRequest(
        @NotBlank(message = "é obrigatório")
        @Size(max = 120, message = "deve ter no máximo 120 caracteres")
        String nome,
        @NotBlank(message = "é obrigatória")
        String descricao,
        String fotoUrl,
        @NotNull(message = "é obrigatória")
        @DecimalMin(value = "0.01", message = "deve ser maior que zero")
        BigDecimal valorUnitario,
        @NotNull(message = "é obrigatório")
        @Positive(message = "deve ser positivo")
        Integer categoriaId) {
}
