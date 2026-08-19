package com.sbedoyab99.alfresco.gestor.demo.repository.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.sbedoyab99.alfresco.gestor.demo.repository.domain.RepositoryStatus;
import com.sbedoyab99.alfresco.gestor.demo.repository.infrastructure.AlfrescoClient;

class RepositoryHealthServiceTests {

	private final AlfrescoClient alfrescoClient = mock(AlfrescoClient.class);
	private final RepositoryHealthService service = new RepositoryHealthService(this.alfrescoClient);

	@Test
	void returnsUpWhenAlfrescoIsAvailable() {
		when(this.alfrescoClient.isAvailable()).thenReturn(true);

		assertThat(this.service.getStatus()).isEqualTo(RepositoryStatus.UP);
	}

	@Test
	void returnsDownWhenAlfrescoIsUnavailable() {
		when(this.alfrescoClient.isAvailable()).thenReturn(false);

		assertThat(this.service.getStatus()).isEqualTo(RepositoryStatus.DOWN);
	}
}
