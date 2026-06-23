package com.pizzeria.pago_service.controller;

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

import com.pizzeria.pago_service.assemblers.PagoModelAssemblers;
import com.pizzeria.pago_service.model.MetodoPago;
import com.pizzeria.pago_service.model.Pago;
import com.pizzeria.pago_service.service.PagoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pagos/v2")
@RequiredArgsConstructor
public class PagoControllerV2 {

    private final PagoService pagoService;
    private final PagoModelAssemblers assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getAllPagos() {
        log.info("GET /pagos/v2 - Solicitando todos los pagos");

        List<EntityModel<Pago>> pagos = pagoService.getAllPagos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pago>> collection = CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> getPagoById(@PathVariable Long id) {
        log.info("GET /pagos/v2/{} - Solicitando pago por ID", id);
        return ResponseEntity.ok(assembler.toModel(pagoService.getPagoById(id)));
    }

    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getPagosByMetodo(
            @PathVariable MetodoPago metodo) {
        log.info("GET /pagos/v2/metodo/{} - Solicitando pagos por método", metodo);

        List<EntityModel<Pago>> pagos = pagoService.getPagosByMetodo(metodo).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pago>> collection = CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).getPagosByMetodo(metodo)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

}
