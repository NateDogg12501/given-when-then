package gwt.test.chain.then.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import gwt.entity.Order;
import gwt.repo.OrderRepo;
import gwt.test.chain.then.ThenAssertChain;

public class OrderRepoAssertChain extends ThenAssertChain {

	private OrderRepo orderRepo;
	
	public OrderRepoAssertChain() {
		super();
		
		orderRepo = this.getBean(OrderRepo.class);
	}
	
	public OrderRepoAssertChain noOrdersExist() {
		assertThat(orderRepo.findAll()).as("Orders do exist").hasSize(0);
		return this;
	}
	
	public OrderRepoAsserts withId(Long id) {
		Optional<Order> potentialOrder = orderRepo.findById(id);
		return new OrderRepoAsserts(potentialOrder);
	}
	
	public class OrderRepoAsserts extends ThenAssertChain {
		
		private Optional<Order> potentialOrder;
		
		private OrderRepoAsserts(Optional<Order> potentialOrder) {
			super();
			
			this.potentialOrder = potentialOrder;
		}
		
		public OrderRepoAsserts exists() {
			assertThat(potentialOrder).overridingErrorMessage("Order does not actually exist").isPresent();
			return this;
		}
		
		public OrderRepoAsserts doesNotExist() {
			assertThat(potentialOrder).overridingErrorMessage("Order actually does exist").isEmpty();
			return this;
		}
		
		public OrderRepoAsserts hasId(Long id) {
			assertThat(potentialOrder.get().getId()).as("Order ID does not match").isEqualTo(id);
			return this;
		}
		
		public OrderRepoAsserts hasTotalPrice(BigDecimal expectedTotalPrice) {
			assertThat(potentialOrder.get().getTotalPrice()).as("Order Price does not match").isEqualTo(expectedTotalPrice);
			return this;
		}
	
		public OrderRepoAsserts hasNumberOfItems(int expectedNumberOfItems) {
			assertThat(potentialOrder.get().getItems()).as("Order Number of Items does not match").hasSize(expectedNumberOfItems);
			return this;
		}
		
		public OrderRepoAsserts hasItemSkus(Long... skus) {
			assertThat(potentialOrder.get().getItems().stream().map(item -> item.getSku())).as("Order Item SKUs do not match").containsOnly(skus);
			return this;
		}
	}
		
}
