package com.pizzeria.envio_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pizzeria.envio_service.dto.EnvioDTO;
import com.pizzeria.envio_service.model.Envio;
import com.pizzeria.envio_service.service.EnvioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/envios")
@RequiredArgsConstructor
public class EnvioController {

    private final EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> getAllEnvios() {
        return ResponseEntity.ok(envioService.getAllEnvios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> getEnvioById(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.getEnvioById(id));
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Envio>> getEnviosByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ini,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(envioService.getEnviosByRangoFechas(ini, fin));
    }

    @PostMapping
    public ResponseEntity<Envio> createEnvio(@Valid @RequestBody EnvioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(envioService.createEnvio(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        envioService.deleteEnvio(id);
        return ResponseEntity.noContent().build();
    }

}
