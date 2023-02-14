package gwt.test.chain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ResponseStatusAsserts extends ThenAssertChain {
	
	public ResponseStatusAsserts(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context, responseEntity, exception);
	}

	public ResponseStatusAsserts isOk() {
		assertEquals(HttpStatus.OK, this.response.getStatusCode());
		return this;
	}
	
	public ResponseStatusAsserts isNotFound() {
		assertEquals(HttpStatus.NOT_FOUND, ((WebClientResponseException) this.exception).getStatusCode());
		return this;
	}
	
	public ResponseStatusAsserts isCreated() {
		assertEquals(HttpStatus.CREATED, this.response.getStatusCode());
		return this;
	}
	
	public ResponseStatusAsserts isUnprocessableEntity() {
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ((WebClientResponseException) this.exception).getStatusCode());
		return this;
	}
	
	public ResponseStatusAsserts isConflict() {
		assertEquals(HttpStatus.CONFLICT, ((WebClientResponseException) this.exception).getStatusCode());
		return this;
	}
	
}
