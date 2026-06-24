package com.pizzeria.direccion_service.controller;

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

import com.pizzeria.direccion_service.dto.DireccionDTO;
import com.pizzeria.direccion_service.model.Direccion;
import com.pizzeria.direccion_service.service.DireccionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/direcciones")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<Direccion>> getAllDirecciones() {
        return ResponseEntity.ok(direccionService.getAllDirecciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> getDireccionById(@PathVariable Long id) {
        return ResponseEntity.ok(direccionService.getDireccionById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Direccion>> getDireccionByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(direccionService.getDireccionByUsuarioId(usuarioId));
    }

    @GetMapping("/default/{usuarioId}")
    public ResponseEntity<Direccion> getDireccionDefaultByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(direccionService.getDireccionDefaultByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Direccion> createDireccion(@Valid @RequestBody DireccionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(direccionService.createDireccion(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direccion> updateDireccion(
            @PathVariable Long id,
            @Valid @RequestBody DireccionDTO dto) {
        return ResponseEntity.ok(direccionService.updateDireccion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDireccion(@PathVariable Long id) {
        direccionService.deleteDireccion(id);
        return ResponseEntity.noContent().build();
    }

}
