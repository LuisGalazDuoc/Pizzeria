package com.pizzeria.pedido_detalle_service.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizzeria.pedido_detalle_service.dto.PedidoDetalleDTO;
import com.pizzeria.pedido_detalle_service.model.PedidoDetalle;
import com.pizzeria.pedido_detalle_service.service.PedidoDetalleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedido-detalles")
@RequiredArgsConstructor
public class PedidoDetalleController {

    private final PedidoDetalleService pedidoDetalleService;

    @GetMapping
    public ResponseEntity<List<PedidoDetalle>> getAllPedidoDetalles() {
        return ResponseEntity.ok(pedidoDetalleService.getAllDetalles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDetalle> getPedidoDetalleById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoDetalleService.getDetalleById(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PedidoDetalle>> getDetalleByPedidoId(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoDetalleService.getDetalleByPedidoId(pedidoId));
    }

    @GetMapping("/subtotal/{pedidoId}")
    public ResponseEntity<BigDecimal> getSubtotalByPedidoId(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoDetalleService.getSubtotalByPedidoId(pedidoId));
    }

    @PostMapping
    public ResponseEntity<PedidoDetalle> createPedidoDetalle(@Valid @RequestBody PedidoDetalleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoDetalleService.createDetalle(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDetalle> updatePedidoDetalle(
            @PathVariable Long id,
            @Valid @RequestBody PedidoDetalleDTO dto) {
        return ResponseEntity.ok(pedidoDetalleService.updateDetalle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoDetalle(@PathVariable Long id) {
        pedidoDetalleService.deleteDetalle(id);
        return ResponseEntity.noContent().build();
    }

}
