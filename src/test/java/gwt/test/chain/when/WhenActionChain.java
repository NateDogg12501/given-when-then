package gwt.test.chain.when;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import gwt.test.chain.BaseChain;
import gwt.test.chain.then.BasicThenAssert;
import gwt.test.chain.then.ThenAssertChain;

public abstract class WhenActionChain extends BaseChain {

	private ResponseEntity<?> response;
	private WebClientResponseException webClientResponseException;
	
	protected static WebClient client;
	
	protected WhenActionChain() {
		super();
	}
	
	public WhenActionChain(ApplicationContext context) {
		super(context);
		
		String port = applicationContext.getEnvironment().getProperty("local.server.port");
		client = WebClient.create("http://localhost:" + port);
	}
	
	public ThenAssertChain then() {
		closeCurrentChain();
		return new BasicThenAssert(applicationContext, response, webClientResponseException);
	}
	
	public ItemControllerActions.WhenGettingAllItemsChain gettingAllItems() {
		closeCurrentChain();
		return ItemControllerActions.whenGettingAllItemsChain();
	}
	
	public ItemControllerActions.WhenGettingItemChain gettingItem() {
		closeCurrentChain();
		return ItemControllerActions.whenGettingItemChain();
	}
	
	public ItemControllerActions.WhenCreatingItemChain creatingItem() {
		closeCurrentChain();
		return ItemControllerActions.whenCreatingItemChain();
	}

	private WhenActionChain closeCurrentChain() {
		try {
			response = sendRequest();
		} catch (WebClientResponseException wcre) {
			webClientResponseException = wcre;
		}
		return this;
	} 
	
	protected abstract ResponseEntity<?> sendRequest();
	
	
}
