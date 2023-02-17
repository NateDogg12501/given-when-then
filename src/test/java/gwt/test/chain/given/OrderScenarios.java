package gwt.test.chain.given;

import java.util.List;
import java.util.stream.Stream;

import gwt.entity.Item;
import gwt.entity.Order;
import gwt.repo.OrderRepo;

public class OrderScenarios extends GivenScenarioChain {

	private OrderRepo orderRepo;
	
	private Order.OrderBuilder orderBuilder;
	
	public OrderScenarios() {
		super();
		
		orderRepo = this.getBean(OrderRepo.class);
		orderBuilder = Order.builder();
	}
	
	public OrderScenarios withId(Long id) {
		orderBuilder.id(id);
		return this;
	}
	
	public OrderScenarios withItemSkus(Long... skus) {
		orderBuilder.items(Stream.of(skus).map(sku -> Item.builder().sku(sku).build()).toList());
		return this;
	}
	
	@Override
	protected GivenScenarioChain closeCurrentChain() {
		orderRepo.save(orderBuilder.build());
		List<Order> allOrders = orderRepo.findAll();
		return this;
	}

}
