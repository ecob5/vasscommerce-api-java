package br.edu.univassouras.vasscommerce.controller;

import br.edu.univassouras.vasscommerce.dto.PedidoFluxoResponse;
import br.edu.univassouras.vasscommerce.service.PedidoFluxoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/pedido", produces = "application/json")
public class PedidoFluxoController {

    private final PedidoFluxoService service;

    public PedidoFluxoController(PedidoFluxoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoFluxoResponse> criar() {
        PedidoFluxoResponse criado = service.criar();
        return ResponseEntity.created(URI.create("/pedido/" + criado.id())).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoFluxoResponse> obter(@PathVariable String id) {
        return ResponseEntity.ok(service.obter(id));
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<PedidoFluxoResponse> pagar(@PathVariable String id) {
        return ResponseEntity.ok(service.pagar(id));
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<PedidoFluxoResponse> enviar(@PathVariable String id) {
        return ResponseEntity.ok(service.enviar(id));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PedidoFluxoResponse> cancelar(@PathVariable String id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
