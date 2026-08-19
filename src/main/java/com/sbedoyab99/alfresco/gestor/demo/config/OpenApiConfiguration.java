package com.sbedoyab99.alfresco.gestor.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

	@Bean
	OpenAPI alfrescoGestorOpenApi() {
		return new OpenAPI().info(new Info()
				.title("Alfresco Gestor API")
				.description("API para gestionar carpetas y documentos en Alfresco Community.")
				.version("v1"));
	}
}
