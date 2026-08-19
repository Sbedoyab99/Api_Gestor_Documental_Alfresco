package com.sbedoyab99.alfresco.gestor.demo.repository.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sbedoyab99.alfresco.gestor.demo.repository.application.RepositoryHealthService;
import com.sbedoyab99.alfresco.gestor.demo.repository.domain.RepositoryStatus;

class RepositoryHealthControllerTests {

	private final RepositoryHealthService service = mock(RepositoryHealthService.class);
	private final RepositoryHealthController controller = new RepositoryHealthController(this.service);

	@Test
	void respondsOkWhenRepositoryIsUp() {
		when(this.service.getStatus()).thenReturn(RepositoryStatus.UP);

		ResponseEntity<RepositoryHealthResponse> response = this.controller.health();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(new RepositoryHealthResponse(RepositoryStatus.UP));
	}

	@Test
	void respondsServiceUnavailableWithoutInternalDetailsWhenRepositoryIsDown() {
		when(this.service.getStatus()).thenReturn(RepositoryStatus.DOWN);

		ResponseEntity<RepositoryHealthResponse> response = this.controller.health();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
		assertThat(response.getBody()).isEqualTo(new RepositoryHealthResponse(RepositoryStatus.DOWN));
	}
}
