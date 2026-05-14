package com.pizzeria.pedido_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;

    @Value("${direccion.service.url}")
    private String direccionServiceUrl;

    @Bean
    public WebClient usuarioWebClient() {
        return WebClient.builder()
                .baseUrl(usuarioServiceUrl)
                .build();
    }

    @Bean
    public WebClient direccionWebClient() {
        return WebClient.builder()
                .baseUrl(direccionServiceUrl)
                .build();
    }

}
