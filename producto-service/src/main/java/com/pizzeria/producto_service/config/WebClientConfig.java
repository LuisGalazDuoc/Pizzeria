package com.pizzeria.producto_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${categoria.service.url}")
    private String categoriaServiceUrl;

    @Bean
    public WebClient categoriaWebClient() {
        return WebClient.builder()
                .baseUrl(categoriaServiceUrl)
                .build();
    }

}
