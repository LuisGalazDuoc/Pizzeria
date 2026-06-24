package com.pizzeria.pago_service.controller;

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

import com.pizzeria.pago_service.dto.PagoDTO;
import com.pizzeria.pago_service.model.MetodoPago;
import com.pizzeria.pago_service.model.Pago;
import com.pizzeria.pago_service.service.PagoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        log.info("GET /pagos - Solicitando todos los pagos");
        return ResponseEntity.ok(pagoService.getAllPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        log.info("GET /pagos/{} - Solicitando pago por ID", id);
        return ResponseEntity.ok(pagoService.getPagoById(id));
    }

    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<List<Pago>> getPagosByMetodo(@PathVariable MetodoPago metodo) {
        log.info("GET /pagos/metodo/{} - Solicitando pagos por metodo", metodo);
        return ResponseEntity.ok(pagoService.getPagosByMetodo(metodo));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalPagos() {
        log.info("GET /pagos/total - Solicitando total recaudado");
        return ResponseEntity.ok(pagoService.getTotalPagos());
    }

    @PostMapping
    public ResponseEntity<Pago> createPago(@Valid @RequestBody PagoDTO dto) {
        log.info("POST /pagos - Creando pago para orden ID: {}", dto.getPedidoId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.createPago(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        log.info("PUT /pagos/{} - Actualizando pago", id);
        return ResponseEntity.ok(pagoService.updatePago(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        log.info("DELETE /pagos/{} - Eliminando pago", id);
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }

}
