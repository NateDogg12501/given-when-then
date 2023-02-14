package gwt.test.chain;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

public class ThenAssertChain extends BaseChain {

	protected ResponseEntity<?> response;
	protected Exception exception;
	
	public ThenAssertChain(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context);
		this.response = responseEntity;
		this.exception = exception;
	}
	
	public ResponseStatusAsserts responseStatus() {
		return new ResponseStatusAsserts(this.context, this.response, this.exception);
	}

	public ResponseBodyAssertChain responseBody() {
		return new ResponseBodyAssertChain(this.context, this.response, this.exception);
	}
	
	public ItemRepoStartingAsserts item() {
		return new ItemRepoStartingAsserts(this.context, this.response, this.exception);
	}
	
}
