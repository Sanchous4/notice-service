package com.example.noticeservice.shared.config.openapi.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    companion object {
        private const val API_TITLE = "Notice Service API"
        private const val API_DESCRIPTION =
            "Documentation of endpoints for creating, reading, updating, and deleting notes."
        private const val API_VERSION = "1.0.0"
        private const val SERVER_URL = "http://localhost:8080"
        private const val SERVER_DESCRIPTION = "Local development server"
    }

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(
                    Contact()
                        .name("API Support")
                        .email("support@example.com")
                        .url("https://example.com")
                )
                .license(
                    License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")
                )
        )
        .servers(
            listOf(
                Server()
                    .url(SERVER_URL)
                    .description(SERVER_DESCRIPTION)
            )
        )
}
