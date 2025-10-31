package com.example.noticeservice.api.shared.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfig {

    @Bean
    fun jacksonCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        }
}
