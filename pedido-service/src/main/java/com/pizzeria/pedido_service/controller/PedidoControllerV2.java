package com.pizzeria.pedido_service.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.pedido_service.assemblers.PedidoModelAssembler;
import com.pizzeria.pedido_service.model.Pedido;
import com.pizzeria.pedido_service.service.PedidoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pedidos/v2")
@RequiredArgsConstructor
public class PedidoControllerV2 {

    private final PedidoService pedidoService;
    private final PedidoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getAllPedidos() {
        log.info("GET /pedidos/v2 - Solicitando todos los pedidos");

        List<EntityModel<Pedido>> pedidos = pedidoService.getAllPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pedido>> collection = CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pedido>> getPedidoById(@PathVariable Long id) {
        log.info("GET /pedidos/v2/{} - Solicitando pedido por ID", id);
        return ResponseEntity.ok(assembler.toModel(pedidoService.getPedidoById(id)));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidosByUsuarioId(
            @PathVariable Long usuarioId) {
        log.info("GET /pedidos/v2/usuario/{} - Solicitando pedidos por usuario", usuarioId);

        List<EntityModel<Pedido>> pedidos = pedidoService.getPedidosByUsuarioId(usuarioId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pedido>> collection = CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getPedidosByUsuarioId(usuarioId)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidosByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        log.info("GET /pedidos/v2/rango-fechas - Rango: {} a {}", inicio, fin);

        List<EntityModel<Pedido>> pedidos = pedidoService.getPedidosByRangoFechas(inicio, fin).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pedido>> collection = CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getPedidosByRangoFechas(inicio, fin)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

}
