package gwt.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gwt.entity.Item;
import gwt.entity.Order;
import gwt.exception.NoItemExists;
import gwt.repo.OrderRepo;

@Service
public class OrderService {

	private OrderRepo orderRepo;
	
	@Autowired
	public OrderService(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	public List<Order> getOrders() {
		return orderRepo.findAll();
	}
	
	public Optional<Order> getOrder(Long id) {
		return orderRepo.findById(id);
	}
	
	public Order createOrder(List<Long> itemSkus) {
		Long itemSku = itemSkus.get(0);
		
		if (itemSku != 1L) {
			throw new NoItemExists("Item with ID [" + itemSku + "] does not exist");
		}
			
		Order order = Order.builder()
			.items(
				Collections.singletonList(
					Item.builder()
						.sku(1L)
						.description("First Item")
						.price(new BigDecimal("1.00"))
					  .build()
				)
			)
		  .build();
		
		return orderRepo.save(order);
	}
	
}
