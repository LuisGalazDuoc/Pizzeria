package com.pizzeria.pedido_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pizzeria.pedido_service.dto.PedidoDTO;
import com.pizzeria.pedido_service.model.Pedido;
import com.pizzeria.pedido_service.service.PedidoService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setPedidoId(1L);
        pedido.setUsuarioId(1L);
        pedido.setDireccionId(1L);
        pedido.setMontoTotal(new BigDecimal("8990.00"));
        pedido.setFechaCreacion(LocalDateTime.now());

        pedidoDTO = new PedidoDTO(1L, 1L, new BigDecimal("8990.00"));
    }

    @Test
    void testObtenerTodosLosPedidos() throws Exception {

        when(pedidoService.getAllPedidos()).thenReturn(List.of(pedido));


        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPedidoPorId() throws Exception {

        when(pedidoService.getPedidoById(1L)).thenReturn(pedido);


        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(1))
                .andExpect(jsonPath("$.montoTotal").value(8990.00));
    }

    @Test
    void testObtenerPedidosPorUsuario() throws Exception {
 
        when(pedidoService.getPedidosByUsuarioId(1L)).thenReturn(List.of(pedido));

 
        mockMvc.perform(get("/pedidos/usuario/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerTotalDePedidos() throws Exception {
 
        when(pedidoService.getTotalPedidos()).thenReturn(new BigDecimal("50000.00"));

 
        mockMvc.perform(get("/pedidos/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(50000.00));
    }

    @Test
    void testObtenerPedidosPorRangoDeFechas() throws Exception {
 
        when(pedidoService.getPedidosByRangoFechas(any(), any())).thenReturn(List.of(pedido));

 
        mockMvc.perform(get("/pedidos/rango-fechas")
                .param("inicio", "2026-01-01T00:00:00")
                .param("fin", "2026-12-31T23:59:59"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePedido() throws Exception {
 
        when(pedidoService.createPedido(any(PedidoDTO.class))).thenReturn(pedido);

 
        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pedidoId").value(1))
                .andExpect(jsonPath("$.montoTotal").value(8990.00));
    }

    @Test
    void testActualizarPedido() throws Exception {
 
        when(pedidoService.updatePedido(eq(1L), any(PedidoDTO.class))).thenReturn(pedido);

    
        mockMvc.perform(put("/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(1))
                .andExpect(jsonPath("$.montoTotal").value(8990.00));
    }

    @Test
    void testEliminarPedido() throws Exception {
 
        doNothing().when(pedidoService).deletePedido(1L);

        mockMvc.perform(delete("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido Eliminado Exitosamente"));
        verify(pedidoService, times(1)).deletePedido(1L);
    }

}
