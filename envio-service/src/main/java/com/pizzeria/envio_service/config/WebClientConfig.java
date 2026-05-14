package com.pizzeria.envio_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${pedido.service.url}")
    private String pedidoServiceUrl;

    @Bean
    public WebClient pedidoWebClient() {
        return WebClient.builder()
                .baseUrl(pedidoServiceUrl)
                .build();
    }

}
