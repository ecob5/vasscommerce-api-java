package br.edu.univassouras.vasscommerce.controller;

import br.edu.univassouras.vasscommerce.model.Dados.Categoria;
import br.edu.univassouras.vasscommerce.model.Dados.Cliente;
import br.edu.univassouras.vasscommerce.model.Dados.Estado;
import br.edu.univassouras.vasscommerce.model.Dados.TipoCartao;
import br.edu.univassouras.vasscommerce.service.VassCommerceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class VassCommerceController {

    private final VassCommerceService service;

    public VassCommerceController(VassCommerceService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Map<String, String> inicio() {
        return Map.of(
                "nome", "API do VassCommerce",
                "versao", "1.0.0",
                "mensagem", "Serviço em funcionamento"
        );
    }

    @GetMapping("/categoria")
    public List<Categoria> listarCategorias(@RequestParam(required = false) String nome) {
        return service.listarCategorias(nome);
    }

    @GetMapping("/categoria/{idcategoria}/produto")
    public Map<String, Object> listarProdutosDaCategoria(@PathVariable int idcategoria) {
        return service.listarProdutosDaCategoria(idcategoria);
    }

    @GetMapping("/produto/{id}")
    public Map<String, Object> buscarProduto(@PathVariable int id) {
        return service.buscarProduto(id);
    }

    @GetMapping("/cliente/{id}")
    public Cliente buscarCliente(@PathVariable int id) {
        return service.buscarCliente(id);
    }

    @GetMapping("/cliente/{idcliente}/formas-de-pagamento")
    public List<Map<String, Object>> listarFormasDePagamento(@PathVariable int idcliente) {
        return service.listarFormasDePagamento(idcliente);
    }

    @GetMapping("/cliente/{idcliente}/endereco")
    public Map<String, Object> buscarEndereco(@PathVariable int idcliente) {
        return service.buscarEndereco(idcliente);
    }

    @GetMapping("/tipo-cartao")
    public List<TipoCartao> listarTiposCartao() {
        return service.listarTiposCartao();
    }

    @GetMapping("/estado")
    public List<Estado> listarEstados() {
        return service.listarEstados();
    }

    @GetMapping("/estado/{idestado}/cidade")
    public Map<String, Object> listarCidadesDoEstado(@PathVariable int idestado) {
        return service.listarCidadesDoEstado(idestado);
    }

    @GetMapping("/cliente/{idcliente}/pedido")
    public List<Map<String, Object>> listarPedidosDoCliente(@PathVariable int idcliente) {
        return service.listarPedidosDoCliente(idcliente);
    }
}
