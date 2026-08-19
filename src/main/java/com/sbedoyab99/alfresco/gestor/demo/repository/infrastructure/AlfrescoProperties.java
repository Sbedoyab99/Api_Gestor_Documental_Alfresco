package com.sbedoyab99.alfresco.gestor.demo.repository.infrastructure;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "alfresco")
public record AlfrescoProperties(
		String baseUrl,
		String username,
		String password,
		Duration connectionTimeout,
		Duration readTimeout) {
}
