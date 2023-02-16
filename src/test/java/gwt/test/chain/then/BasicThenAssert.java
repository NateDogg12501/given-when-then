package gwt.test.chain.then;

import org.junit.jupiter.api.Assertions;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

public class BasicThenAssert extends ThenAssertChain {

	public BasicThenAssert(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context, responseEntity, exception);
	}
	
	public ThenAssertChain fail() {
		Assertions.fail("Explicitly failing test");
		return this;
	}
	
}
