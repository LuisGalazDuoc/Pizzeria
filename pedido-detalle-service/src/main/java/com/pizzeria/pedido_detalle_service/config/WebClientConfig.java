package com.pizzeria.pedido_detalle_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${pedido.service.url}")
    private String pedidoServiceUrl;

    @Value("${producto.service.url}")
    private String productoServiceUrl;

    @Bean
    public WebClient pedidoWebClient() {
        return WebClient.builder()
                .baseUrl(pedidoServiceUrl)
                .build();
    }

    @Bean
    public WebClient productoWebClient() {
        return WebClient.builder()
                .baseUrl(productoServiceUrl)
                .build();
    }

}
