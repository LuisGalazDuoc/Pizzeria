package com.pizzeria.producto_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.pizzeria.producto_service.dto.ProductoDTO;
import com.pizzeria.producto_service.model.Producto;
import com.pizzeria.producto_service.repository.ProductoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private WebClient categoriaWebClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        producto = new Producto(1L, 1L, "Pizza Margherita",
                "Salsa de tomate, mozzarella y albahaca", new BigDecimal("7990.00"), true);

        productoDTO = new ProductoDTO(1L, "Pizza Margherita",
                "Salsa de tomate, mozzarella y albahaca", new BigDecimal("7990.00"), true);
    }

    @Test
    void testObtenerProductosDisponibles() {
        when(productoRepository.findByDisponibleTrue()).thenReturn(List.of(producto));

        List<Producto> resultado = productoService.getProductosDisponibles();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDisponible()).isTrue();
    }

    @Test
    void testObtenerProductoPorId() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.getProductoById(1L);

        assertThat(resultado.getNombre()).isEqualTo("Pizza Margherita");
        assertThat(resultado.getPrecio()).isEqualByComparingTo("7990.00");
    }

    @Test
    void testCrearProducto() {
        when(categoriaWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.createProducto(productoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Pizza Margherita");
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testActualizarProducto() {
        ProductoDTO productoActualizadoDTO = new ProductoDTO(
                2L,
                "Pizza Pepperoni",
                "Salsa de tomate, pepperoni y mozzarella",
                new BigDecimal("10990.00"),
                false
        );

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(categoriaWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.updateProducto(1L, productoActualizadoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCategoriaId()).isEqualTo(2L);
        assertThat(resultado.getNombre()).isEqualTo("Pizza Pepperoni");
        assertThat(resultado.getDescripcion()).isEqualTo("Salsa de tomate, pepperoni y mozzarella");
        assertThat(resultado.getPrecio()).isEqualByComparingTo("10990.00");
        assertThat(resultado.getDisponible()).isFalse();
        assertThat(producto.getNombre()).isEqualTo("Pizza Pepperoni");
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testEliminarProducto() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        productoService.deleteProducto(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }

}
