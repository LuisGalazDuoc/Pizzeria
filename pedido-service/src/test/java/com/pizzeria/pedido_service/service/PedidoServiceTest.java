package com.pizzeria.pedido_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.pizzeria.pedido_service.dto.PedidoDTO;
import com.pizzeria.pedido_service.model.Pedido;
import com.pizzeria.pedido_service.repository.PedidoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private WebClient usuarioWebClient;

    @Mock
    private WebClient direccionWebClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;
    
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        pedidoService = new PedidoService(pedidoRepository, usuarioWebClient, direccionWebClient);

        pedido = new Pedido();
        pedido.setPedidoId(1L);
        pedido.setUsuarioId(1L);
        pedido.setDireccionId(1L);
        pedido.setMontoTotal(new BigDecimal("8990.00"));

        pedidoDTO = new PedidoDTO(1L, 1L, new BigDecimal("8990.00"));
    }

    @Test
    void testObtenerPedidoPorId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido result = pedidoService.getPedidoById(1L);

        assertThat(result.getMontoTotal()).isEqualByComparingTo("8990.00");
    }

    @Test
    void testCalcularTotalPedidos() {
        when(pedidoRepository.sumMontoTotalPedido()).thenReturn(new BigDecimal("50000.00"));

        BigDecimal result = pedidoService.getTotalPedidos();

        assertThat(result).isEqualByComparingTo("50000.00");
    }

    @Test
    void testCrearPedidoSiUsuarioYDireccionSonValidos() {

        when(usuarioWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(direccionWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.createPedido(pedidoDTO);

        assertThat(resultado).isNotNull();
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testEliminarPedido() {
        Long id = 1L;
        when(pedidoRepository.existsById(id)).thenReturn(true);
        pedidoService.deletePedido(id);
        verify(pedidoRepository).deleteById(id);
    }

    //VALIDACIONES ESPECIFICAS

    private BigDecimal calculadoraDescuento(BigDecimal monto, BigDecimal porcentajeDescuento) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if (porcentajeDescuento == null ||
                porcentajeDescuento.compareTo(BigDecimal.ZERO) < 0 ||
                porcentajeDescuento.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }

        BigDecimal discountFactor = BigDecimal.ONE.subtract(
                porcentajeDescuento.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));

        return monto.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }

    @Test
    void aplicacionDescuento10Porc() {
        BigDecimal monto = new BigDecimal("10000.00");
        BigDecimal descuento = new BigDecimal("10");

        BigDecimal result = calculadoraDescuento(monto, descuento);

        assertThat(result).isEqualByComparingTo("9000.00");
    }

    @Test
    void retornarMontoSinDescuento() {
        BigDecimal monto = new BigDecimal("8990.00");

        BigDecimal resultado = calculadoraDescuento(monto, BigDecimal.ZERO);

        assertThat(resultado).isEqualByComparingTo("8990.00");
    }

    @Test
    void errorDescuentoPorcSobre100() {
        BigDecimal monto = new BigDecimal("5000.00");
        BigDecimal descuentoInvalido = new BigDecimal("150");

        assertThatThrownBy(() -> calculadoraDescuento(monto, descuentoInvalido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("debe estar entre 0 y 100");
    }

}
