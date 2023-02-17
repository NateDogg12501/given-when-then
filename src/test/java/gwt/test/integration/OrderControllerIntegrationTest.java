package gwt.test.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import gwt.dto.request.OrderRequestDto;
import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;
import gwt.entity.Item;
import gwt.entity.Order;
import gwt.repo.ItemRepo;
import gwt.repo.OrderRepo;

public class OrderControllerIntegrationTest extends BaseIntegrationTest {
	
	@Test
	void getOrders() {
		
		given()
			.existingItem()
				.withSku(1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("1.00"))
			.existingItem()
				.withSku(2L)
				.withDescription("Second Item")
				.withPrice(new BigDecimal("2.00"))
			.existingItem()
				.withSku(3L)
				.withDescription("Third Item")
				.withPrice(new BigDecimal("3.00"))
			.existingOrder()
				.withId(1L)
				.withItemSkus(1L, 2L, 3L)
			.existingOrder()
				.withId(2L)
				.withItemSkus(1L, 2L)
			.when()
				.gettingAllOrders();
		
		
		WebClient client = WebClient.create("http://localhost:" + port);

		ResponseEntity<List<OrderResponseDto>> response = client.get().uri("/order").retrieve().toEntityList(OrderResponseDto.class).block();
		
		assertEquals(200, response.getStatusCodeValue());
		
		List<OrderResponseDto> result = response.getBody();

		assertEquals(2, result.size());
		
		OrderResponseDto order1 = result.get(0);
		assertEquals(1L, order1.getId());
		assertTrue(order1.getTotalPrice().compareTo(new BigDecimal("6.00")) == 0);
		assertEquals(3, order1.getItems().size());
		
		assertItem(order1.getItems().get(0), 1L, "First Item", new BigDecimal("1.00"));
		assertItem(order1.getItems().get(1), 2L, "Second Item", new BigDecimal("2.00"));
		assertItem(order1.getItems().get(2), 3L, "Third Item", new BigDecimal("3.00"));
		
		OrderResponseDto order2 = result.get(1);
		assertEquals(2L, order2.getId());
		assertTrue(order2.getTotalPrice().compareTo(new BigDecimal("3.00")) == 0);
		assertEquals(2, order2.getItems().size());
		
		assertItem(order2.getItems().get(0), 1L, "First Item", new BigDecimal("1.00"));
		assertItem(order2.getItems().get(1), 2L, "Second Item", new BigDecimal("2.00"));
	}
	
	@Test
	void getOrder_happyPath() {
		Item item1 = createItem(1L, "First Item", new BigDecimal("1.00"));
		Item item2 = createItem(2L, "Second Item", new BigDecimal("2.00"));
		Item item3 = createItem(3L, "Third Item", new BigDecimal("3.00"));
		Order order = createOrder(item1, item2, item3);
		
		WebClient client = WebClient.create("http://localhost:" + port);

		ResponseEntity<OrderResponseDto> response = client.get().uri("/order/" + order.getId()).retrieve().toEntity(OrderResponseDto.class).block();
		
		assertEquals(200, response.getStatusCodeValue());
		
		OrderResponseDto result = response.getBody();
		
		assertEquals(order.getId(), result.getId());
		assertTrue(result.getTotalPrice().compareTo(new BigDecimal("6.00")) == 0);
		assertEquals(3, result.getItems().size());
		
		assertItem(result.getItems().get(0), 1L, "First Item", new BigDecimal("1.00"));
		assertItem(result.getItems().get(1), 2L, "Second Item", new BigDecimal("2.00"));
		assertItem(result.getItems().get(2), 3L, "Third Item", new BigDecimal("3.00"));
	}
	
	@Test
	void getOrder_invalidId() {
		WebClient client = WebClient.create("http://localhost:" + port);

		try {
			client.get().uri("/order/1").retrieve().toEntity(OrderResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(404, exceptionResponse.getRawStatusCode());
		}
	}
	
	@Test
	void createOrder_happyPath() {
		createItem(1L, "First Item", new BigDecimal("1.00"));
		
		WebClient client = WebClient.create("http://localhost:" + port);

		OrderRequestDto request = OrderRequestDto.builder()
									.itemSkus(
										Collections.singletonList(1L)
									)
									.build();
		ResponseEntity<OrderResponseDto> response = client.post().uri("/order").bodyValue(request).retrieve().toEntity(OrderResponseDto.class).block();
		
		assertEquals(201, response.getStatusCodeValue());
		
		OrderResponseDto result = response.getBody();
		
		assertTrue(result.getTotalPrice().compareTo(new BigDecimal("1.00")) == 0);
		assertEquals(1, result.getItems().size());
		assertItem(result.getItems().get(0), 1L, "First Item", new BigDecimal("1.00"));
		
		Optional<Order> potentialOrder = orderRepo.findById(result.getId());
		assertTrue(potentialOrder.isPresent());
		assertTrue(potentialOrder.get().getTotalPrice().compareTo(new BigDecimal("1.00")) == 0);
		assertEquals(1, potentialOrder.get().getItems().size());
		
		Item i = potentialOrder.get().getItems().get(0);
		assertEquals(1L,i.getSku());
		assertTrue(i.getDescription().equals("First Item"));
		assertTrue(i.getPrice().compareTo(new BigDecimal("1.00")) == 0);		
	}
	
	@Test
	void createOrder_invalidItemSku() {
		WebClient client = WebClient.create("http://localhost:" + port);

		try {
			OrderRequestDto request = OrderRequestDto.builder()
					.itemSkus(
							Collections.singletonList(
								1L
							)
						)
						.build();
			
			client.post().uri("/order").bodyValue(request).retrieve().toEntity(OrderResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(422, exceptionResponse.getRawStatusCode());
		}
		
		List<Order> allOrders = orderRepo.findAll();
		assertEquals(0, allOrders.size());
	}
	
	@Test
	void createOrder_noItems() {
		WebClient client = WebClient.create("http://localhost:" + port);

		try {
			OrderRequestDto request = OrderRequestDto.builder()
					.itemSkus(
						Collections.emptyList()
					)
					.build();
			
			client.post().uri("/order").bodyValue(request).retrieve().toEntity(OrderResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(422, exceptionResponse.getRawStatusCode());
		}
		
		List<Order> allOrders = orderRepo.findAll();
		assertEquals(0, allOrders.size());
	}
	
	private Item createItem(long sku, String description, BigDecimal price) {
		return itemRepo.save(Item.builder()
				.sku(sku)
				.description(description)
				.price(price)
				.build()
			);
	}
	
	private Order createOrder(Item... items) {
		return orderRepo.save(Order.builder()
				.items(Arrays.asList(items))
				.build()
			);
	}
	
	private void assertItem(ItemResponseDto actual, long expectedSku, String expectedDescription, BigDecimal expectedPrice) {
		assertEquals(expectedSku, actual.getSku());
		assertTrue(actual.getDescription().equals(expectedDescription));
		assertTrue(actual.getPrice().compareTo(expectedPrice) == 0);
	}
	
}
