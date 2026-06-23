package com.pizzeria.envio_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.envio_service.controller.EnvioControllerV2;
import com.pizzeria.envio_service.model.Envio;

@Component
public class EnvioModelAssembler {

    public EntityModel<Envio> toModel(Envio envio) {
        EntityModel<Envio> model = EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerV2.class).getEnvioById(envio.getEnvioId()))
                        .withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios())
                        .withRel("all-envios"));

        model.add(Link.of("http://localhost:8080/orders/" + envio.getPedidoId())
                .withRel("order"));

        return model;
    }

}
