package com.pizzeria.pago_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.pizzeria.pago_service.dto.PagoDTO;
import com.pizzeria.pago_service.model.EstadoPago;
import com.pizzeria.pago_service.model.MetodoPago;
import com.pizzeria.pago_service.model.Pago;
import com.pizzeria.pago_service.repository.PagoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private WebClient orderWebClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private PagoDTO pagoDTO;

    @BeforeEach
    void setUp() {
        pago = new Pago(1L, 1L, new BigDecimal("8990.00"), 
                    MetodoPago.Credito, EstadoPago.Pendiente, null);

        pagoDTO = new PagoDTO(1L, new BigDecimal("8990.00"), 
                        MetodoPago.Credito, EstadoPago.Pendiente);
    }

    @Test
    void testCrearPago() {
        when(orderWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());
        when(pagoRepository.existsByPedidoId(1L)).thenReturn(false);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Pago resultado = pagoService.createPago(pagoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo(EstadoPago.Pendiente);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void shouldCalculateTotalPayments() {
        when(pagoRepository.sumTotalPago()).thenReturn(new BigDecimal("125000.00"));

        BigDecimal result = pagoService.getTotalPagos();

        assertThat(result).isEqualByComparingTo("125000.00");
    }

}
