package com.pizzeria.producto_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.producto_service.assemblers.ProductoModelAssembler;
import com.pizzeria.producto_service.model.Producto;
import com.pizzeria.producto_service.service.ProductoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos/v2")
@RequiredArgsConstructor
public class ProductoControllerV2 {

    private final ProductoService productoService;
    private final ProductoModelAssembler assembler;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.getAllProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Producto>> collection = CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(productoService.getProductoById(id)));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByCategoryId(
            @PathVariable Long categoriaId) {
        List<EntityModel<Producto>> productos = productoService.getProductosByCategoriaId(categoriaId)
                .stream().map(assembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<Producto>> collection = CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCategoryId(categoriaId)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/disponible")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosDisponibles() {
        List<EntityModel<Producto>> productos = productoService.getProductosDisponibles().stream()
                .map(assembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<Producto>> collection = CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getProductosDisponibles()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

}
