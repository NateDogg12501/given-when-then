package gwt.test.chain;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class WhenActionChain extends BaseChain {

	protected static ResponseEntity<?> response;
	protected static Exception exception;
	
	public WhenActionChain(ApplicationContext context) {
		super(context);
	}
	
	public ItemControllerActions.WhenGettingAllItemsChain gettingAllItems() {
		closeCurrentChain();
		return new ItemControllerActions(context).new WhenGettingAllItemsChain(context);
	}
	
	public ItemControllerActions.WhenGettingItemChain gettingItem() {
		closeCurrentChain();
		return new ItemControllerActions(context).new WhenGettingItemChain(context);
	}
	
	public ItemControllerActions.WhenCreatingItemChain creatingItem() {
		closeCurrentChain();
		return new ItemControllerActions(context).new WhenCreatingItemChain(context);
	}

	protected WhenActionChain closeCurrentChain() {
		try {
			response = sendRequest();
		} catch (WebClientResponseException wcre) {
			exception = wcre;
		}
		return this;
	} 
	
	protected ResponseEntity<?> sendRequest() {
		return null;
	}
	
	public ThenAssertChain then() {
		closeCurrentChain();
		return new ThenAssertChain(this.context, response, exception);
	}
}
