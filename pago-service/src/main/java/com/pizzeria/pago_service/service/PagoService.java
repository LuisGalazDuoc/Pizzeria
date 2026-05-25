package com.pizzeria.pago_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pizzeria.pago_service.dto.PagoDTO;
import com.pizzeria.pago_service.model.EstadoPago;
import com.pizzeria.pago_service.model.MetodoPago;
import com.pizzeria.pago_service.model.Pago;
import com.pizzeria.pago_service.repository.PagoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient pedidoWebClient;

    public List<Pago> getAllPagos() {
        log.info("Obteniendo todos los pagos");
        List<Pago> pagos = pagoRepository.findAll();
        log.debug("Total pagos encontrados: {}", pagos.size());
        return pagos;
    }

    public Pago getPagoById(Long id) {
        log.info("Buscando pago con ID: {}", id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pago no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Pago no encontrado con ID: " + id);
                });
    }

    public List<Pago> getPagosByMetodo(MetodoPago metodo) {
        log.info("Buscando pagos con metodo: {}", metodo);
        List<Pago> pagos = pagoRepository.findByMetodo(metodo);
        log.debug("Pagos encontrados con metodo {}: {}", metodo, pagos.size());
        return pagos;
    }

    public BigDecimal getTotalPagos() {
        log.info("Calculando total recaudado");
        BigDecimal total = pagoRepository.sumTotalPago();
        log.debug("Total recaudado: {}", total);
        return total;
    }

    public Pago createPago(PagoDTO dto) {
        log.info("Creando pago para pedido ID: {}", dto.getPedidoId());
        validatePedidoExists(dto.getPedidoId());
        log.debug("Pedido ID: {} validado correctamente", dto.getPedidoId());

        if (pagoRepository.existsByPedidoId(dto.getPedidoId())) {
            log.warn("Pago duplicado para pedido ID: {}", dto.getPedidoId());
            throw new IllegalArgumentException(
                    "Ya existe un pago para el pedido ID: " + dto.getPedidoId()
            );
        }
        Pago pago = new Pago();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMonto(dto.getMonto());
        pago.setMetodo(dto.getMetodo());
        pago.setEstado(EstadoPago.Pendiente);

        Pago guardado = pagoRepository.save(pago);
        log.info("Pago creado exitosamente con ID: {}", guardado.getPagoId());
        return guardado;
    }

    public Pago updatePago(Long id, PagoDTO dto) {
        log.info("Actualizando pago con ID: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pago no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Pago no encontrado con ID: " + id);
                });

        pago.setMonto(dto.getMonto());
        pago.setMetodo(dto.getMetodo());
        pago.setEstado(dto.getEstado());
        if (dto.getEstado() == EstadoPago.Completado && pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDateTime.now());
            log.debug("Pago ID: {} marcado como completado en: {}", id, pago.getFechaPago());
        }

        Pago actualizado = pagoRepository.save(pago);
        log.info("Pago actualizado exitosamente con ID: {}", actualizado.getPagoId());
        return actualizado;
    }

    public void deletePago(Long id) {
        log.info("Eliminando pago con ID: {}", id);
        if (!pagoRepository.existsById(id)) {
            log.warn("Intento de eliminar pago inexistente con ID: {}", id);
            throw new IllegalArgumentException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
        log.info("Pago eliminado exitosamente con ID: {}", id);
    }

    // comunicacion con ms pedido
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
