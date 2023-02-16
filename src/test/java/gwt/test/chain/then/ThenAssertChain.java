package gwt.test.chain.then;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import gwt.test.chain.BaseChain;

public abstract class ThenAssertChain extends BaseChain {

	protected static ResponseEntity<?> responseEntity;
	protected static Exception exception;
	
	protected ThenAssertChain() {
		super();
	} 
	
	public ThenAssertChain(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context);
		
		ThenAssertChain.responseEntity = responseEntity;
		ThenAssertChain.exception = exception;
	}
		
	public ResponseStatusAsserts responseStatus() {
		return new ResponseStatusAsserts();
	}

	public ResponseBodyAssertChain responseBody() {
		return new ResponseBodyAssertChain();
	}
	
	public RepositoryAssertChain repository() {
		return new RepositoryAssertChain();
	}
	
}
