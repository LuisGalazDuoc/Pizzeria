package com.pizzeria.pedido_service.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pizzeria.pedido_service.dto.PedidoDTO;
import com.pizzeria.pedido_service.model.Pedido;
import com.pizzeria.pedido_service.service.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        log.info("GET /pedidos - Solicitando todos los pedidos");
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        log.info("GET /pedidos/{} - Solicitando pedido por ID", id);
        return ResponseEntity.ok(pedidoService.getPedidoById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> getPedidosByUsuarioId(@PathVariable Long usuarioId) {
        log.info("GET /pedidos/usuario/{} - Solicitando pedidos por usuario", usuarioId);
        return ResponseEntity.ok(pedidoService.getPedidosByUsuarioId(usuarioId));
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Pedido>> getPedidosByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        log.info("GET /pedidos/rango-fechas - Rango: {} a {}", inicio, fin);
        return ResponseEntity.ok(pedidoService.getPedidosByRangoFechas(inicio, fin));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalPedidos() {
        log.info("GET /pedidos/total - Solicitando total acumulado");
        return ResponseEntity.ok(pedidoService.getTotalPedidos());
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@Valid @RequestBody PedidoDTO dto) {
        log.info("POST /pedidos - Creando pedido para usuario ID: {}", dto.getUsuarioId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.createPedido(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(
            @PathVariable Long id,
            @Valid @RequestBody PedidoDTO dto) {
        log.info("PUT /pedidos/{} - Actualizando pedido", id);
        return ResponseEntity.ok(pedidoService.updatePedido(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        log.info("DELETE /pedidos/{} - Eliminando pedido", id);
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}
