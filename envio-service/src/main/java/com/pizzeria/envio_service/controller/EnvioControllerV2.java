package com.pizzeria.envio_service.controller;

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

import com.pizzeria.envio_service.assemblers.EnvioModelAssembler;
import com.pizzeria.envio_service.model.Envio;
import com.pizzeria.envio_service.service.EnvioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/envios/v2")
@RequiredArgsConstructor
public class EnvioControllerV2 {

    private final EnvioService envioService;
    private final EnvioModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getAllEnvios() {
        List<EntityModel<Envio>> envios = envioService.getAllEnvios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Envio>> collection = CollectionModel.of(envios,
                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> getEnvioById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(envioService.getEnvioById(id)));
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getEnvioByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<EntityModel<Envio>> envios = envioService.getEnviosByRangoFechas(inicio, fin)
                .stream().map(assembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<Envio>> collection = CollectionModel.of(envios,
                linkTo(methodOn(EnvioControllerV2.class).getEnvioByRangoFechas(inicio, fin)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

}
