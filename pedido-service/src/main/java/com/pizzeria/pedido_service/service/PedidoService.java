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
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos;
    }

    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada con ID: " + id));
    }

    public List<Pedido> getPedidosByUsuarioId(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos;
    }

    public List<Pedido> getPedidosByRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        List<Pedido> pedidos = pedidoRepository.findByFechaCreacionBetween(inicio, fin);
        return pedidos;
    }

    public BigDecimal getTotalPedidos() {
        BigDecimal total = pedidoRepository.sumMontoTotalPedido();
        return total;
    }

    public Pedido createPedido(PedidoDTO dto) {
        validateUsuarioExists(dto.getUsuarioId());
        validateDireccionExists(dto.getDireccionId());
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setDireccionId(dto.getDireccionId());
        pedido.setMontoTotal(dto.getMontoTotal());
        ///pedido.setEstado(pedidoRepository.PENDING);  --->  Para futuro uso
        Pedido guardado = pedidoRepository.save(pedido);
        return guardado;
    }

    public Pedido updatePedido(Long id, PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada con ID: " + id));
        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setDireccionId(dto.getDireccionId());
        pedido.setMontoTotal(dto.getMontoTotal());
        ///order.setStatus(dto.getStatus());    --->    Para futuro uso
        Pedido actualizado = pedidoRepository.save(pedido);
        return actualizado;
    }

    public void deletePedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new IllegalArgumentException("Orden no encontrada con ID: " + id);
        }
        pedidoRepository.deleteById(id);
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
