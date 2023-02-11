package gwt.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

@RestController
@RequestMapping("/order")
public class OrderController {

	@GetMapping
	public ResponseEntity<List<OrderResponseDto>> getOrders() {
		List<OrderResponseDto> orders = new ArrayList<>();
		
		orders.add(
			OrderResponseDto.builder()
				.id(1L)
				.items(
					Arrays.asList(
						ItemResponseDto.builder()
							.id(1L)
							.description("First Item")
							.price(new BigDecimal("1.00"))
						  .build(),
						ItemResponseDto.builder()
							.id(2L)
							.description("Second Item")
							.price(new BigDecimal("2.00"))
						  .build(),
						ItemResponseDto.builder()
							.id(3L)
							.description("Third Item")
							.price(new BigDecimal("3.00"))
						  .build()
					)
				)
				.totalPrice(new BigDecimal("6.00"))
			  .build()
			);

		orders.add(
			OrderResponseDto.builder()
				.id(2L)
				.items(
					Arrays.asList(
						ItemResponseDto.builder()
							.id(1L)
							.description("First Item")
							.price(new BigDecimal("1.00"))
						  .build(),
						ItemResponseDto.builder()
							.id(2L)
							.description("Second Item")
							.price(new BigDecimal("2.00"))
						  .build()
					)
				)
				.totalPrice(new BigDecimal("3.00"))
			  .build()
			);
		
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("{orderId}")
	public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
		if (orderId != 1L) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		OrderResponseDto order =	OrderResponseDto.builder()
			.id(1L)
			.items(
				Arrays.asList(
					ItemResponseDto.builder()
						.id(1L)
						.description("First Item")
						.price(new BigDecimal("1.00"))
					  .build(),
					ItemResponseDto.builder()
						.id(2L)
						.description("Second Item")
						.price(new BigDecimal("2.00"))
					  .build(),
					ItemResponseDto.builder()
						.id(3L)
						.description("Third Item")
						.price(new BigDecimal("3.00"))
					  .build()
				)
			)
			.totalPrice(new BigDecimal("6.00"))
		  .build();
		
		return ResponseEntity.ok(order);
	}
	
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
		if (orderRequestDto.getItems().isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		ItemRequestDto item = orderRequestDto.getItems().get(0);
		
		if (item.getId() != 1L) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		if (item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		OrderResponseDto order = OrderResponseDto.builder()
			.id(1L)
			.items(
				Collections.singletonList(
					ItemResponseDto.builder()
						.id(1L)
						.description("First Item")
						.price(new BigDecimal("1.00"))
					  .build()
				)
			)
			.totalPrice(new BigDecimal("1.00"))
		  .build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
	}
	
}
