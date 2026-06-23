package com.example.usuario_service.controller;

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

import com.example.usuario_service.assemblers.UsuarioModelAssembler;
import com.example.usuario_service.model.Usuario;
import com.example.usuario_service.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuarios/v2")
@RequiredArgsConstructor
public class UsuarioControllerV2 {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios() {
        log.info("GET /usuarios/v2 - Solicitando todos los usuarios");

        List<EntityModel<Usuario>> usuarios = usuarioService.getAllUsuarios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Usuario>> lista = CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        log.info("GET /usuarios/v2/{} - Solicitando usuario por ID", id);
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioByRut(@PathVariable Long rut) {
        log.info("GET /usuarios/v2/rut/{} - Solicitando usuario por RUT", rut);
        Usuario usuario = usuarioService.getUsuarioByRut(rut);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

}
