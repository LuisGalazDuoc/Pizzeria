package com.pizzeria.envio_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pizzeria.envio_service.dto.EnvioDTO;
import com.pizzeria.envio_service.model.Envio;
import com.pizzeria.envio_service.repository.EnvioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final WebClient pedidoWebClient;

    public List<Envio> getAllPedidos() {
        return envioRepository.findAll();
    }

    public Envio getPedidoById(Long id) {
        return envioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Delivery no encontrado con ID: " + id
                ));
    }

    public List<Envio> getPedidosByRangoFechas(LocalDateTime ini, LocalDateTime fin) {
        return envioRepository.findByFechaEnvioBetween(ini, fin);
    }

    public Envio createEnvio(EnvioDTO dto) {
        validatePedidoExists(dto.getPedidoId());

        if (envioRepository.existsByPedidoId(dto.getPedidoId()))
            throw new IllegalArgumentException(
                    "Ya existe un delivery para la orden ID: " + dto.getPedidoId()
            );

        Envio envio = new Envio();
        envio.setPedidoId(dto.getPedidoId());
        return envioRepository.save(envio);
    }

    public void deleteEnvio(Long id) {
        if (!envioRepository.existsById(id))
            throw new IllegalArgumentException("Delivery no encontrado con ID: " + id);
        envioRepository.deleteById(id);
    }

    // Comunicacion con ms pedido
    private void validatePedidoExists(Long pedidoId) {
        try {
            pedidoWebClient.get()
                    .uri("/pedidos/{id}", pedidoId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException(
                    "La orden con ID " + pedidoId + " no existe en la base de datos"
            );
        }
    }

}
