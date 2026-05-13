package com.example.usuario_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.usuario_service.dto.UsuarioDTO;
import com.example.usuario_service.model.Usuario;
import com.example.usuario_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado con ID: " + id
                ));
    }

    public Usuario getUsuarioByRut(Long rut) {
        return usuarioRepository.findByRut(rut)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado con RUT: " + rut
                ));
    }

    public Usuario createUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByRut(dto.getRut()))
            throw new IllegalArgumentException("Ya existe un usuario con el RUT: " + dto.getRut());
        if (usuarioRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());

        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setDvrut(dto.getDvrut());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado con ID: " + id
                ));

        if (!usuario.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());

        usuario.setRut(dto.getRut());
        usuario.setDvrut(dto.getDvrut());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        usuarioRepository.deleteById(id);
    }

}
