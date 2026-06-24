package com.pizzeria.categoria_service.controller;

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

import com.pizzeria.categoria_service.assemblers.CategoriaModelAssembler;
import com.pizzeria.categoria_service.model.Categoria;
import com.pizzeria.categoria_service.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias/v2")
@RequiredArgsConstructor
public class CategoriaControllerV2 {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Categoria>>> getAllCategorias() {
        List<EntityModel<Categoria>> categorias = categoriaService.getAllCategorias().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Categoria>> collection = CollectionModel.of(categorias,
                linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> getCategoriaById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(categoriaService.getCategoriaById(id)));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EntityModel<Categoria>> getCategoriaByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(assembler.toModel(categoriaService.getCategoriaByNombre(nombre)));
    }

}
