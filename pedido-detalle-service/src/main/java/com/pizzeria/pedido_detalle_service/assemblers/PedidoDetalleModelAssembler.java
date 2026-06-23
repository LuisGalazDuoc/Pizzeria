package com.pizzeria.pedido_detalle_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.pedido_detalle_service.controller.PedidoDetalleController;
import com.pizzeria.pedido_detalle_service.model.PedidoDetalle;

@Component
public class PedidoDetalleModelAssembler {

    public EntityModel<PedidoDetalle> toModel(PedidoDetalle detalle) {
        EntityModel<PedidoDetalle> model = EntityModel.of(detalle,
                linkTo(methodOn(PedidoDetalleController.class).getPedidoDetalleById(detalle.getDetalleId()))
                        .withSelfRel(),
                linkTo(methodOn(PedidoDetalleController.class).getDetalleByPedidoId(detalle.getPedidoId()))
                        .withRel("detalle-por-pedido"));

        model.add(Link.of("http://localhost:9090/pedidos/" + detalle.getPedidoId())
                .withRel("pedido"));
        model.add(Link.of("http://localhost:9090/productos/" + detalle.getProductoId())
                .withRel("producto"));

        return model;
    }

}
