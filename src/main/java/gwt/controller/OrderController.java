package gwt.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gwt.dto.request.ItemRequestDto;
import gwt.dto.request.OrderRequestDto;
import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;
import gwt.entity.Item;
import gwt.entity.Order;
import gwt.exception.NoItemExists;
import gwt.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponseDto>> getOrders() {
		List<OrderResponseDto> orders = new ArrayList<>();
		
		for (Order order : orderService.getOrders()) {
			orders.add(mapEntityToDto(order));
		}
		
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("{orderId}")
	public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
		Optional<Order> order = orderService.getOrder(orderId);
		
		if (order.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.ok(mapEntityToDto(order.get()));
	}
	
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
		if (orderRequestDto.getItemSkus().isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		try {
			Order newOrder = orderService.createOrder(orderRequestDto.getItemSkus());
			return ResponseEntity.status(HttpStatus.CREATED).body(mapEntityToDto(newOrder));
		} catch (NoItemExists nie) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	private OrderResponseDto mapEntityToDto(Order order) {
		OrderResponseDto dto = new OrderResponseDto();
		
		dto.setId(order.getId());
		dto.setTotalPrice(order.getTotalPrice());
		dto.setItems(new ArrayList<>());
		for (Item item : order.getItems()) {
			dto.getItems().add(
				ItemResponseDto.builder()
					.sku(item.getSku())
					.description(item.getDescription())
					.price(item.getPrice())
				  .build()
			);
		}
		
		return dto;
	}
	
}
