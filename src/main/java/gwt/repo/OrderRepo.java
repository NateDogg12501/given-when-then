package gwt.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gwt.entity.Item;
import gwt.entity.Order;

@Repository
public class OrderRepo {

	public List<Order> findAll() {
		List<Order> orders = new ArrayList<>();
		
		orders.add(
			Order.builder()
				.id(1L)
				.items(
					Arrays.asList(
						Item.builder()
							.sku(1L)
							.description("First Item")
							.price(new BigDecimal("1.00"))
						  .build(),
						Item.builder()
							.sku(2L)
							.description("Second Item")
							.price(new BigDecimal("2.00"))
						  .build(),
						Item.builder()
							.sku(3L)
							.description("Third Item")
							.price(new BigDecimal("3.00"))
						  .build()
					)
				)
			  .build()
			);

		orders.add(
			Order.builder()
				.id(2L)
				.items(
					Arrays.asList(
						Item.builder()
							.sku(1L)
							.description("First Item")
							.price(new BigDecimal("1.00"))
						  .build(),
						Item.builder()
							.sku(2L)
							.description("Second Item")
							.price(new BigDecimal("2.00"))
						  .build()
					)
				)
			  .build()
			);
		
		return orders;
	}
	
	public Optional<Order> findById(Long id) {
		Map<Long, Order> orders = new HashMap<>();
		
		Order order = Order.builder()
				.id(1L)
				.items(
					Arrays.asList(
						Item.builder()
							.sku(1L)
							.description("First Item")
							.price(new BigDecimal("1.00"))
						  .build(),
						Item.builder()
							.sku(2L)
							.description("Second Item")
							.price(new BigDecimal("2.00"))
						  .build(),
						Item.builder()
							.sku(3L)
							.description("Third Item")
							.price(new BigDecimal("3.00"))
						  .build()
					)
				)
			  .build();
		
		orders.put(1L, order);
		
		return Optional.ofNullable(orders.get(id));
	}
	
	public Order save(Order order) {
		order.setId(1L);
		
		return order;
	}
	
}
