package com.pizzeria.direccion_service.controller;

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

import com.pizzeria.direccion_service.assemblers.DireccionModelAssembler;
import com.pizzeria.direccion_service.model.Direccion;
import com.pizzeria.direccion_service.service.DireccionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/direcciones/v2")
@RequiredArgsConstructor
public class DireccionControllerV2 {

    private final DireccionService direccionService;
    private final DireccionModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Direccion>>> getAllDirecciones() {
        List<EntityModel<Direccion>> direcciones = direccionService.getAllDirecciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Direccion>> collection = CollectionModel.of(direcciones,
                linkTo(methodOn(DireccionControllerV2.class).getAllDirecciones()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Direccion>> getDireccionById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(direccionService.getDireccionById(id)));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<EntityModel<Direccion>>> getDireccionByUsuarioId(
            @PathVariable Long usuarioId) {
        List<EntityModel<Direccion>> direcciones = direccionService.getDireccionByUsuarioId(usuarioId)
                .stream().map(assembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<Direccion>> collection = CollectionModel.of(direcciones,
                linkTo(methodOn(DireccionControllerV2.class).getDireccionByUsuarioId(usuarioId)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

}
