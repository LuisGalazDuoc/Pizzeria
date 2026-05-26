package com.pizzeria.pedido_detalle_service.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pizzeria.pedido_detalle_service.model.PedidoDetalle;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

    List<PedidoDetalle> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long pedidoId);

    @Query("SELECT COALESCE(SUM(pd.subtotal), 0) FROM PedidoDetalle pd WHERE pd.pedidoId = :pedidoId")
    BigDecimal sumSubtotalByPedidoId(@Param("pedido_id") Long pedidoId);

}
