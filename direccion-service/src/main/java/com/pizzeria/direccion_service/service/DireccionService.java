package com.pizzeria.direccion_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pizzeria.direccion_service.dto.DireccionDTO;
import com.pizzeria.direccion_service.model.Direccion;
import com.pizzeria.direccion_service.repository.DireccionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final WebClient usuarioWebClient;

    public List<Direccion> getAllDirecciones() {
        return direccionRepository.findAll();
    }

    public Direccion getDireccionById(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Dirección no encontrada con ID: " + id
                ));
    }

    public List<Direccion> getDireccionByUsuarioId(Long usuarioId) {
        return direccionRepository.findByUsuarioId(usuarioId);
    }

    public Direccion getDireccionDefaultByUsuarioId(Long usuarioId) {
        return direccionRepository.findByUsuarioIdAndEsDefaultTrue(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe dirección por defecto para el cliente ID: " + usuarioId
                ));
    }

    public Direccion createDireccion(DireccionDTO dto) {
        validateUsuarioExists(dto.getUsuarioId());

        Direccion direccion = new Direccion();
        direccion.setUsuarioId(dto.getUsuarioId());
        direccion.setCalle(dto.getCalle());
        direccion.setComuna(dto.getComuna());
        direccion.setEsDefault(dto.getEsDefault());

        return direccionRepository.save(direccion);
    }

    public Direccion updateDireccion(Long id, DireccionDTO dto) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Dirección no encontrada con ID: " + id
                ));

        direccion.setCalle(dto.getCalle());
        direccion.setComuna(dto.getComuna());
        direccion.setEsDefault(dto.getEsDefault());

        return direccionRepository.save(direccion);
    }

    public void deleteDireccion(Long id) {
        if (!direccionRepository.existsById(id))
            throw new IllegalArgumentException("Dirección no encontrada con ID: " + id);
        direccionRepository.deleteById(id);
    }

    /* METODO PARA LA COMUNICACION CON MS USUARIO-SERVICE  */ 
    private void validateUsuarioExists(Long usuarioId) {
        try {
            usuarioWebClient.get()
                    .uri("/usuarios/{id}", usuarioId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException(
                    "El cliente con ID " + usuarioId + " no existe en la base de datos"
            );
        }
    }

}
