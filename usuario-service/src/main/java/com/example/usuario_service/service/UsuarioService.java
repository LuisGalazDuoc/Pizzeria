package com.example.usuario_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.usuario_service.dto.UsuarioDTO;
import com.example.usuario_service.model.Usuario;
import com.example.usuario_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        log.info("Obteniendo todos los usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.debug("Total usuarios encontrados: {}", usuarios.size());
        return usuarios;
    }

    public Usuario getUsuarioById(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Usuario no encontrado con ID: " + id);
                });
    }

    public Usuario getUsuarioByRut(Long rut) {
        log.info("Buscando usuario con RUT: {}", rut);
        return usuarioRepository.findByRut(rut)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con RUT: {}", rut);
                    return new IllegalArgumentException("Usuario no encontrado con RUT: " + rut);
                });
    }

    public Usuario createUsuario(UsuarioDTO dto) {
        log.info("Creando usuario con RUT: {}", dto.getRut());
        if (usuarioRepository.existsByRut(dto.getRut())) {
            log.warn("RUT duplicado: {}", dto.getRut());
            throw new IllegalArgumentException("Ya existe un usuario con el RUT: " + dto.getRut());
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            log.warn("Email duplicado: {}", dto.getEmail());
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setDvrut(dto.getDvrut());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        Usuario save = usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente con ID: {}", save.getUsuarioId());
        return save;
    }

    public Usuario updateUsuario(Long id, UsuarioDTO dto) {
        log.info("Actualizando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Usuario no encontrado con ID: " + id);
                });

        if (!usuario.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail())) {
            log.warn("Email duplicado en actualización: {}", dto.getEmail());
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        usuario.setRut(dto.getRut());
        usuario.setDvrut(dto.getDvrut());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        Usuario update = usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente con ID: {}", update.getUsuarioId());
        return update;
    }

    public void deleteUsuario(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            log.warn("Intento de eliminar usuario inexistente con ID: {}", id);
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado exitosamente con ID: {}", id);
    }

}
