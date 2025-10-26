package com.example.noticeservice.shared.config.openapi.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.result.view.RedirectView


@Hidden
@RestController
@Profile("dev")
class SwaggerRedirectController {

    @GetMapping("/docs")
    fun redirectToSwagger() =
        RedirectView("/swagger-ui.html")
}
