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
import com.pizzeria.pago_service.model.Pago;
import com.pizzeria.pago_service.service.PagoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        return ResponseEntity.ok(pagoService.getAllPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.getPagoById(id));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalPagos() {
        return ResponseEntity.ok(pagoService.getTotalPagos());
    }

    @PostMapping
    public ResponseEntity<Pago> createPago(@RequestBody PagoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.createPago(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> updatePago(
            @PathVariable Long id,
            @RequestBody PagoDTO dto) {
        return ResponseEntity.ok(pagoService.updatePago(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }

}
