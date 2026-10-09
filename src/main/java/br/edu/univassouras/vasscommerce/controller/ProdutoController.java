package br.edu.univassouras.vasscommerce.controller;

import br.edu.univassouras.vasscommerce.dto.ProdutoCreateRequest;
import br.edu.univassouras.vasscommerce.dto.ProdutoResponse;
import br.edu.univassouras.vasscommerce.mapper.ProdutoMapper;
import br.edu.univassouras.vasscommerce.model.ProdutoModel;
import br.edu.univassouras.vasscommerce.model.Dados.Produto;
import br.edu.univassouras.vasscommerce.exception.CadastroNaoEncontradoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produto", produces = "application/json")
public class ProdutoController {

    private final ProdutoModel model;

    public ProdutoController(@Qualifier("produtoModelMemoria") ProdutoModel model) {
        this.model = model;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoCreateRequest request) {
        Produto criado = model.criar(request);
        URI location = URI.create("/produto/" + criado.id());
        return ResponseEntity.created(location).body(toResponse(criado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> obter(@PathVariable Integer id) {
        validarId(id);
        Produto produto = model.obterPorId(id)
                .orElseThrow(() -> new CadastroNaoEncontradoException("Produto não encontrado."));
        return ResponseEntity.ok(toResponse(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo) {
        if (valorMinimo != null && valorMinimo.signum() < 0
                || valorMaximo != null && valorMaximo.signum() < 0) {
            throw new IllegalArgumentException("Os valores de filtro não podem ser negativos.");
        }
        if (valorMinimo != null && valorMaximo != null && valorMinimo.compareTo(valorMaximo) > 0) {
            throw new IllegalArgumentException("valorMinimo não pode ser maior que valorMaximo.");
        }
        List<ProdutoResponse> resposta = model.buscar(nome, valorMinimo, valorMaximo)
                .stream().map(this::toResponse).toList();
        return ResponseEntity.ok(resposta);
    }

    private ProdutoResponse toResponse(Produto produto) {
        return ProdutoMapper.toResponse(produto, model.precoAtual(produto));
    }

    private static void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O id de produto deve ser um número inteiro positivo.");
        }
    }
}
