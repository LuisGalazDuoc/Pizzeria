package com.pizzeria.pedido_detalle_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzeria.pedido_detalle_service.dto.PedidoDetalleDTO;
import com.pizzeria.pedido_detalle_service.model.PedidoDetalle;
import com.pizzeria.pedido_detalle_service.service.PedidoDetalleService;

@WebMvcTest(PedidoDetalleController.class)
public class PedidoDetalleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PedidoDetalleService pedidoDetalleService;

    private PedidoDetalle pedidoDetalle;
    private PedidoDetalleDTO pedidoDetalleDTO;

    @BeforeEach
    void setUp() {
        pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setDetalleId(1L);
        pedidoDetalle.setPedidoId(1L);
        pedidoDetalle.setProductoId(1L);
        pedidoDetalle.setCantidad(2);
        pedidoDetalle.setPrecioUnidad(new BigDecimal("7990.00"));
        pedidoDetalle.setSubtotal(new BigDecimal("15980.00"));

        pedidoDetalleDTO = new PedidoDetalleDTO(1L, 1L, 2, new BigDecimal("7990.00"));
    }

    @Test
    void testObtenerTodosLosDetallesDelPedido() throws Exception {
 
        when(pedidoDetalleService.getAllDetalles()).thenReturn(List.of(pedidoDetalle));

 
        mockMvc.perform(get("/pedido-detalles"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerDetalleDelPedidoPorId() throws Exception {
 
        when(pedidoDetalleService.getDetalleById(1L)).thenReturn(pedidoDetalle);

 
        mockMvc.perform(get("/pedido-detalles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.detalleId").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.subtotal").value(15980.00));
    }

    @Test
    void testObtenerDetallePorPedidoId() throws Exception {
 
        when(pedidoDetalleService.getDetalleByPedidoId(1L)).thenReturn(List.of(pedidoDetalle));

 
        mockMvc.perform(get("/pedido-detalles/pedido/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerSubtotalDelPedido() throws Exception {
 
        when(pedidoDetalleService.getSubtotalByPedidoId(1L))
                .thenReturn(new BigDecimal("15980.00"));

 
        mockMvc.perform(get("/pedido-detalles/subtotal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(15980.00));
    }

    @Test
    void testCrearDetalleDelPedido() throws Exception {
 
        when(pedidoDetalleService.createDetalle(any(PedidoDetalleDTO.class)))
                .thenReturn(pedidoDetalle);

 
        mockMvc.perform(post("/pedido-detalles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDetalleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subtotal").value(15980.00));
    }

    @Test
    void testActualizarDetalleDelPedido() throws Exception {
 
        when(pedidoDetalleService.updateDetalle(eq(1L), any(PedidoDetalleDTO.class)))
                .thenReturn(pedidoDetalle);

 
        mockMvc.perform(put("/pedido-detalles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDetalleDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarDetalleDelPedido() throws Exception {
 
        doNothing().when(pedidoDetalleService).deleteDetalle(1L);


        mockMvc.perform(delete("/pedido-detalles/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Detalle del Pedido Eliminado Exitosamente"));
        verify(pedidoDetalleService, times(1)).deleteDetalle(1L);
    }

}
