package com.tuevento.generador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api.endpoint}")
    private String endpoint;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Bean
    public WebClient geminiWebClient() {
        return WebClient.builder()
                .baseUrl(endpoint)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                // ampliar tamaño de buffer si es necesario
                .exchangeStrategies(ExchangeStrategies.builder()
                    .codecs(configurer -> 
                        configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                    .build())
                .build();
    }
}
