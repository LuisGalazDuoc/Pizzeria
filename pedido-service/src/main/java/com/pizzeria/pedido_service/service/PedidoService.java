package com.pizzeria.pedido_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pizzeria.pedido_service.dto.PedidoDTO;
import com.pizzeria.pedido_service.model.Pedido;
import com.pizzeria.pedido_service.repository.PedidoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final WebClient usuarioWebClient;
    private final WebClient direccionWebClient;

    public PedidoService(PedidoRepository pedidoRepository,
                        @Qualifier("usuarioWebClient") WebClient usuarioWebClient,
                        @Qualifier("direccionWebClient") WebClient direccionWebClient) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioWebClient = usuarioWebClient;
        this.direccionWebClient = direccionWebClient;
    }

    public List<Pedido> getAllPedidos() {
        log.info("Obteniendo todos los pedidos");
        List<Pedido> pedidos = pedidoRepository.findAll();
        log.debug("Total pedidos encontrados: {}", pedidos.size());
        return pedidos;
    }

    public Pedido getPedidoById(Long id) {
        log.info("Buscando pedido con ID: {}", id);
        return pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Orden no encontrada con ID: " + id);
                });
    }

    public List<Pedido> getPedidosByUsuarioId(Long usuarioId) {
        log.info("Buscando pedidos del usuario ID: {}", usuarioId);
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        log.debug("Pedidos encontrados para usuario {}: {}", usuarioId, pedidos.size());
        return pedidos;
    }

    public List<Pedido> getPedidosByRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        log.info("Buscando pedidos entre {} y {}", inicio, fin);
        List<Pedido> pedidos = pedidoRepository.findByFechaCreacionBetween(inicio, fin);
        log.debug("Pedidos encontrados: {}", pedidos.size());
        return pedidos;
    }

    public BigDecimal getTotalPedidos() {
        log.info("Calculando total acumulado de pedidos");
        BigDecimal total = pedidoRepository.sumMontoTotalPedido();
        log.debug("Total acumulado: {}", total);
        return total;
    }

    public Pedido createPedido(PedidoDTO dto) {
        log.info("Creando pedido para usuario ID: {}", dto.getUsuarioId());
        validateUsuarioExists(dto.getUsuarioId());
        log.debug("Usuario ID: {} validado correctamente", dto.getUsuarioId());
        validateDireccionExists(dto.getDireccionId());
        log.debug("Dirección ID: {} validada correctamente", dto.getDireccionId());

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setDireccionId(dto.getDireccionId());
        pedido.setMontoTotal(dto.getMontoTotal());
        ///pedido.setEstado(pedidoRepository.PENDING);  --->  Para futuro uso
        
        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido creado exitosamente con ID: {}", guardado.getPedidoId());
        return guardado;
    }

    public Pedido updatePedido(Long id, PedidoDTO dto) {
        log.info("Actualizando pedido con ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Pedido no encontrada con ID: " + id);
                });

        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setDireccionId(dto.getDireccionId());
        pedido.setMontoTotal(dto.getMontoTotal());
        ///pedido.setStatus(dto.getEstado());    --->    Para futuro uso
        
        Pedido actualizado = pedidoRepository.save(pedido);
        log.info("Pedido actualizado exitosamente con ID: {}", actualizado.getPedidoId());
        return actualizado;
    }

    public void deletePedido(Long id) {
        log.info("Eliminando pedido con ID: {}", id);
        if (!pedidoRepository.existsById(id)) {
            log.warn("Intento de eliminar pedido inexistente con ID: {}", id);
            throw new IllegalArgumentException("Orden no encontrada con ID: " + id);
        }
        pedidoRepository.deleteById(id);
        log.info("Pedido eliminado exitosamente con ID: {}", id);
    }

    // Comunicacion con los ms usuario y direccion
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

    private void validateDireccionExists(Long direccionId) {
        try {
            direccionWebClient.get()
                    .uri("/direcciones/{id}", direccionId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException(
                    "La dirección con ID " + direccionId + " no existe en la base de datos"
            );
        }
    }

}
