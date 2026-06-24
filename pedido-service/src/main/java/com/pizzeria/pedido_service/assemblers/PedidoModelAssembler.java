package com.pizzeria.pedido_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.pedido_service.controller.PedidoControllerV2;
import com.pizzeria.pedido_service.model.Pedido;

@Component
public class PedidoModelAssembler {
    
    public EntityModel<Pedido> toModel(Pedido pedido) {
        EntityModel<Pedido> model = EntityModel.of(pedido,
                linkTo(methodOn(PedidoControllerV2.class).getPedidoById(pedido.getPedidoId()))
                        .withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos())
                        .withRel("pedidos"),
                linkTo(methodOn(PedidoControllerV2.class).getPedidosByUsuarioId(pedido.getUsuarioId()))
                        .withRel("pedidos-del-usuario"));

        model.add(Link.of("http://localhost:9090/usuarios/" + pedido.getUsuarioId())
                .withRel("usuarios"));
        model.add(Link.of("http://localhost:9090/direcciones/" + pedido.getDireccionId())
                .withRel("direcciones"));
        model.add(Link.of("http://localhost:9090/pedido-detalles/pedido/" + pedido.getPedidoId())
                .withRel("pedido-detalles"));
        model.add(Link.of("http://localhost:9090/pagos")
                .withRel("pagos"));
        model.add(Link.of("http://localhost:9090/envios")
                .withRel("envios"));

        return model;
    }

}
