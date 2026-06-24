package com.pizzeria.pago_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.pago_service.controller.PagoControllerV2;
import com.pizzeria.pago_service.model.Pago;

@Component
public class PagoModelAssemblers {

    public EntityModel<Pago> toModel(Pago pago) {
        EntityModel<Pago> model = EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).getPagoById(pago.getPagoId()))
                        .withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).getAllPagos())
                        .withRel("pagos"));

        model.add(Link.of("http://localhost:9090/pedidos/" + pago.getPedidoId())
                .withRel("pedidos"));

        return model;
    }

}
