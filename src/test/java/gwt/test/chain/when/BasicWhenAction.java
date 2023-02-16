package gwt.test.chain.when;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

public class BasicWhenAction extends WhenActionChain {

	public BasicWhenAction(ApplicationContext context) {
		super(context);
	}
	
	public WhenActionChain noActionsAreTaken() {
		return this;
	}
	
	@Override
	protected ResponseEntity<?> sendRequest() {
		return null;
	}

}
