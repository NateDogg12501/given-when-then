package gwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gwt.entity.Item;
import gwt.entity.Order;
import gwt.exception.NoItemExists;
import gwt.repo.ItemRepo;
import gwt.repo.OrderRepo;

@Service
public class OrderService {

	private OrderRepo orderRepo;
	private ItemRepo itemRepo;
	
	@Autowired
	public OrderService(OrderRepo orderRepo, ItemRepo itemRepo) {
		this.orderRepo = orderRepo;
		this.itemRepo = itemRepo;
	}
	
	public List<Order> getOrders() {
		return orderRepo.findAll();
	}
	
	public Optional<Order> getOrder(Long id) {
		return orderRepo.findById(id);
	}
	
	public Order createOrder(List<Long> itemSkus) {
		List<Item> items = new ArrayList<>();
		
		for (Long itemSku : itemSkus) {
			Optional<Item> item = itemRepo.findById(itemSku);
			if (item.isEmpty()) {
				throw new NoItemExists("Item with ID [" + itemSku + "] does not exist");
			}
			
			items.add(item.get());
		}
			
		Order order = Order.builder().items(items).build();
		
		return orderRepo.save(order);
	}
	
}
