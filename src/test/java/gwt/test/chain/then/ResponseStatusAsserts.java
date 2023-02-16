package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ResponseStatusAsserts extends ThenAssertChain {
	
	public ResponseStatusAsserts() {
		super();
	}

	public ResponseStatusAsserts isOk() {
		assertThat(responseEntity.getStatusCode()).as("Response Status Code does not match").isEqualTo(HttpStatus.OK);
		return this;
	}
	
	public ResponseStatusAsserts isCreated() {
		assertThat(responseEntity.getStatusCode()).as("Response Status Code does not match").isEqualTo(HttpStatus.CREATED);
		return this;
	}
	
	public ResponseStatusAsserts isNotFound() {
		assertThat(((WebClientResponseException) exception).getStatusCode()).as("Response Status Code does not match").isEqualTo(HttpStatus.NOT_FOUND);
		return this;
	}
	
	public ResponseStatusAsserts isUnprocessableEntity() {
		assertThat(((WebClientResponseException) exception).getStatusCode()).as("Response Status Code does not match").isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		return this;
	}
	
	public ResponseStatusAsserts isConflict() {
		assertThat(((WebClientResponseException) exception).getStatusCode()).as("Response Status Code does not match").isEqualTo(HttpStatus.CONFLICT);
		return this;
	}
	
}
