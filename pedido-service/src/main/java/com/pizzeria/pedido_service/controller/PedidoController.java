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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.getPedidoById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> getPedidosByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.getPedidosByUsuarioId(usuarioId));
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Pedido>> getPedidosByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(pedidoService.getPedidosByRangoFechas(inicio, fin));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalPedidos() {
        return ResponseEntity.ok(pedidoService.getTotalPedidos());
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody PedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.createPedido(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(
            @PathVariable Long id,
            @RequestBody PedidoDTO dto) {
        return ResponseEntity.ok(pedidoService.updatePedido(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}
