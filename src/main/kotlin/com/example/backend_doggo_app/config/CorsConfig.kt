package com.example.backend_doggo_app.config
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@ConfigurationProperties(prefix = "cors")
class CorsConfig {
    @Bean
fun corsConfigurationSource(): CorsConfigurationSource? {

    val corsConfiguration = CorsConfiguration()
    corsConfiguration.allowedOrigins = listOf("*")
    corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE","PATCH")
    corsConfiguration.allowedHeaders = listOf("*")
    corsConfiguration.allowCredentials = true

    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", corsConfiguration)

    return source
}
}