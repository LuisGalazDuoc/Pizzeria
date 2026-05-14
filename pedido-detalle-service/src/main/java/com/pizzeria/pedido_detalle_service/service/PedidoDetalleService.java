package com.pizzeria.pedido_detalle_service.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pizzeria.pedido_detalle_service.dto.PedidoDetalleDTO;
import com.pizzeria.pedido_detalle_service.model.PedidoDetalle;
import com.pizzeria.pedido_detalle_service.repository.PedidoDetalleRepository;

@Service
public class PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final WebClient pedidoWebClient;
    private final WebClient productoWebClient;

    public PedidoDetalleService(PedidoDetalleRepository pedidoDetalleRepository,
                              @Qualifier("pedidoWebClient") WebClient pedidoWebClient,
                              @Qualifier("productoWebClient") WebClient productoWebClient) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoWebClient = pedidoWebClient;
        this.productoWebClient = productoWebClient;
    }

    public List<PedidoDetalle> getAllDetalles() {
        return pedidoDetalleRepository.findAll();
    }

    public PedidoDetalle getDetalleById(Long id) {
        return pedidoDetalleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Detalle no encontrado con ID: " + id
                ));
    }

    public List<PedidoDetalle> getDetalleByPedidoId(Long pedidoId) {
        return pedidoDetalleRepository.findByPedidoId(pedidoId);
    }

    public BigDecimal getSubtotalByPedidoId(Long pedidoId) {
        return pedidoDetalleRepository.sumSubtotalByPedidoId(pedidoId);
    }

    public PedidoDetalle createDetalle(PedidoDetalleDTO dto) {
        validatePedidoExists(dto.getPedidoId());
        validateProductoExists(dto.getProductoId());

        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setPedidoId(dto.getPedidoId());
        detalle.setProductoId(dto.getProductoId());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnidad(dto.getPrecioUnidad());

        return pedidoDetalleRepository.save(detalle);
    }

    public PedidoDetalle updateDetalle(Long id, PedidoDetalleDTO dto) {
        PedidoDetalle detalle = pedidoDetalleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Detalle no encontrado con ID: " + id
                ));

        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnidad(dto.getPrecioUnidad());
        detalle.setSubtotal(dto.getPrecioUnidad()
                .multiply(BigDecimal.valueOf(dto.getCantidad())));

        return pedidoDetalleRepository.save(detalle);
    }

    public void deleteDetalle(Long id) {
        if (!pedidoDetalleRepository.existsById(id))
            throw new IllegalArgumentException("Detalle no encontrado con ID: " + id);
        pedidoDetalleRepository.deleteById(id);
    }

    // Comunicacion con ms pedido y producto
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

    private void validateProductoExists(Long productoId) {
        try {
            productoWebClient.get()
                    .uri("/productos/{id}", productoId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException(
                    "El producto con ID " + productoId + " no existe en la base de datos"
            );
        }
    }

}
