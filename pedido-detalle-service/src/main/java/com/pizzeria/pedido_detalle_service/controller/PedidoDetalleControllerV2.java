package com.pizzeria.pedido_detalle_service.controller;

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

import com.pizzeria.pedido_detalle_service.assemblers.PedidoDetalleModelAssembler;
import com.pizzeria.pedido_detalle_service.model.PedidoDetalle;
import com.pizzeria.pedido_detalle_service.service.PedidoDetalleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedido-detalles/v2")
@RequiredArgsConstructor
public class PedidoDetalleControllerV2 {

    private final PedidoDetalleService pedidoDetalleService;
    private final PedidoDetalleModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PedidoDetalle>>> getAllPedidoDetalles() {
        List<EntityModel<PedidoDetalle>> detalles = pedidoDetalleService.getAllDetalles().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<PedidoDetalle>> collection = CollectionModel.of(detalles,
                linkTo(methodOn(PedidoDetalleControllerV2.class).getAllPedidoDetalles()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PedidoDetalle>> getPedidoDetalleById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(pedidoDetalleService.getDetalleById(id)));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<CollectionModel<EntityModel<PedidoDetalle>>> getDetalleByPedidoId(
            @PathVariable Long pedidoId) {
        List<EntityModel<PedidoDetalle>> detalles = pedidoDetalleService.getDetalleByPedidoId(pedidoId)
                .stream().map(assembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<PedidoDetalle>> collection = CollectionModel.of(detalles,
                linkTo(methodOn(PedidoDetalleControllerV2.class).getDetalleByPedidoId(pedidoId)).withSelfRel());

        return ResponseEntity.ok(collection);
    }



}
