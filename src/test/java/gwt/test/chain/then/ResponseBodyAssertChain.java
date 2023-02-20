package gwt.test.chain.then;

import gwt.test.chain.then.response.CreateItemAsserts;
import gwt.test.chain.then.response.CreateOrderAsserts;

public class ResponseBodyAssertChain extends ThenAssertChain {

	public ResponseBodyAssertChain() {
		super();
	}
	
	public GetAllItemsAsserts hasGetAllItemsResponseBody() {
		return new GetAllItemsAsserts();
	}
	
	public GetItemBySkuAsserts hasGetItemBySkuResponseBody() {
		return new GetItemBySkuAsserts();
	}
	
	public CreateItemAsserts hasCreateItemResponseBody() {
		return new CreateItemAsserts();
	}
	
	public GetAllOrdersAsserts hasGetAllOrdersResponseBody() {
		return new GetAllOrdersAsserts();
	}
	
	public GetOrderAsserts hasGetOrderResponseBody() {
		return new GetOrderAsserts();
	}
	
	public CreateOrderAsserts hasCreateOrderResponseBody() {
		return new CreateOrderAsserts();
	}

}
