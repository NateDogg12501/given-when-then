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
	
	public ItemControllerActions.WhenGettingAllItemsAction gettingAllItems() {
		closeCurrentChain();
		return ItemControllerActions.whenGettingAllItemsAction();
	}
	
	public ItemControllerActions.WhenGettingItemAction gettingItem() {
		closeCurrentChain();
		return ItemControllerActions.whenGettingItemAction();
	}
	
	public ItemControllerActions.WhenCreatingItemAction creatingItem() {
		closeCurrentChain();
		return ItemControllerActions.whenCreatingItemAction();
	}

	public OrderControllerActions.WhenGettingAllOrdersAction gettingAllOrders() {
		closeCurrentChain();
		return OrderControllerActions.whenGettingAllOrdersAction();
	}
	
	public OrderControllerActions.WhenGettingOrderAction gettingOrder() {
		closeCurrentChain();
		return OrderControllerActions.whenGettingOrderAction();
	}
	
	public OrderControllerActions.WhenCreatingOrderAction creatingOrder() {
		closeCurrentChain();
		return OrderControllerActions.whenCreatingOrderAction();
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
