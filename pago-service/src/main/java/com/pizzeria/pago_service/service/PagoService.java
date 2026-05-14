package com.pizzeria.pago_service.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pizzeria.pago_service.dto.PagoDTO;
import com.pizzeria.pago_service.model.Pago;
import com.pizzeria.pago_service.repository.PagoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient pedidoWebClient;

    public List<Pago> getAllPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagos;
    }

    public Pago getPagoById(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id));
    }

    public BigDecimal getTotalPagos() {
        BigDecimal total = pagoRepository.sumTotalPago();
        return total;
    }

    public Pago createPago(PagoDTO dto) {
        validatePedidoExists(dto.getPedidoId());
        if (pagoRepository.existsByPedidoId(dto.getPedidoId())) {
            throw new IllegalArgumentException(
                    "Ya existe un pago para la orden ID: " + dto.getPedidoId()
            );
        }
        Pago pago = new Pago();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMonto(dto.getMonto());
        Pago guardado = pagoRepository.save(pago);
        return guardado;
    }

    public Pago updatePago(Long id, PagoDTO dto) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id)
                );
        pago.setMonto(dto.getMonto());
        Pago actualizado = pagoRepository.save(pago);
        return actualizado;
    }

    public void deletePago(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new IllegalArgumentException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
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
