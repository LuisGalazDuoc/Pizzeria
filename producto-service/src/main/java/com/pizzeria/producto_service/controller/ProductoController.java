package com.pizzeria.producto_service.controller;

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

import com.pizzeria.producto_service.dto.ProductoDTO;
import com.pizzeria.producto_service.model.Producto;
import com.pizzeria.producto_service.service.ProductoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> getProductsByCategoryId(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(productoService.getProductosByCategoriaId(categoriaId));
    }

    @GetMapping("/disponible")
    public ResponseEntity<List<Producto>> getProductosDisponibles() {
        return ResponseEntity.ok(productoService.getProductosDisponibles());
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody ProductoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.createProducto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Long id,
            @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productoService.updateProducto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

}
