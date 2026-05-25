package com.example.usuario_service.controller;

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

import com.example.usuario_service.dto.UsuarioDTO;
import com.example.usuario_service.model.Usuario;
import com.example.usuario_service.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        log.info("GET /usuarios - Solicitando todos los usuarios");
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        log.info("GET /usuarios/{} - Solicitando usuario por ID", id);
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Usuario> getUsuarioByRut(@PathVariable Long rut) {
        log.info("GET /usuarios/rut/{} - Solicitando usuario por RUT", rut);
        return ResponseEntity.ok(usuarioService.getUsuarioByRut(rut));
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO dto) {
        log.info("POST /usuarios - Creando usuario con RUT: {}", dto.getRut());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.createUsuario(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        log.info("PUT /usuarios/{} - Actualizando usuario", id);
        return ResponseEntity.ok(usuarioService.updateUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        log.info("DELETE /usuarios/{} - Eliminando usuario", id);
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
