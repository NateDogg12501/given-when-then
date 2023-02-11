package gwt.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import gwt.dto.request.ItemRequestDto;
import gwt.dto.request.OrderRequestDto;
import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;

public class OrderControllerTest extends BaseTest {

	@Test
	void getOrders() {
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
		WebClient client = WebClient.create("http://localhost:" + port);

		ResponseEntity<OrderResponseDto> response = client.get().uri("/order/1").retrieve().toEntity(OrderResponseDto.class).block();
		
		assertEquals(200, response.getStatusCodeValue());
		
		OrderResponseDto result = response.getBody();
		
		assertEquals(1L, result.getId());
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
			ResponseEntity<OrderResponseDto> response = client.get().uri("/order/2").retrieve().toEntity(OrderResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(404, exceptionResponse.getRawStatusCode());
		}
	}
	
	@Test
	void createOrder_happyPath() {
		WebClient client = WebClient.create("http://localhost:" + port);

		OrderRequestDto request = OrderRequestDto.builder()
									.items(
										Collections.singletonList(
											ItemRequestDto.builder()
												.id(1L)
												.description("First Item")
												.price(new BigDecimal("1.00"))
											  .build()
										)
									)
									.build();
		ResponseEntity<OrderResponseDto> response = client.post().uri("/order").bodyValue(request).retrieve().toEntity(OrderResponseDto.class).block();
		
		assertEquals(201, response.getStatusCodeValue());
		
		OrderResponseDto result = response.getBody();
		
		assertEquals(1L, result.getId());
		assertTrue(result.getTotalPrice().compareTo(new BigDecimal("1.00")) == 0);
		assertEquals(1, result.getItems().size());
		
		assertItem(result.getItems().get(0), 1L, "First Item", new BigDecimal("1.00"));
		
		//TODO check repo
	}
	
	@Test
	void createOrder_invalidItemId() {
		WebClient client = WebClient.create("http://localhost:" + port);

		try {
			OrderRequestDto request = OrderRequestDto.builder()
					.items(
							Collections.singletonList(
								ItemRequestDto.builder()
									.id(2L)
									.description("First Item")
									.price(new BigDecimal("1.00"))
								  .build()
							)
						)
						.build();
			
			ResponseEntity<OrderResponseDto> response = client.post().uri("/order").bodyValue(request).retrieve().toEntity(OrderResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(422, exceptionResponse.getRawStatusCode());
		}
		
		//TODO check repo
	}
	
	@Test
	void createOrder_badItemPrice() {
		WebClient client = WebClient.create("http://localhost:" + port);

		try {
			OrderRequestDto request = OrderRequestDto.builder()
					.items(
							Collections.singletonList(
								ItemRequestDto.builder()
									.id(1L)
									.description("First Item")
									.price(new BigDecimal("-1.00"))
								  .build()
							)
						)
						.build();
			
			ResponseEntity<OrderResponseDto> response = client.post().uri("/order").bodyValue(request).retrieve().toEntity(OrderResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(422, exceptionResponse.getRawStatusCode());
		}
		
		//TODO check repo
	}
	
	@Test
	void createOrder_noItems() {
		WebClient client = WebClient.create("http://localhost:" + port);

		try {
			OrderRequestDto request = OrderRequestDto.builder()
					.items(
						Collections.emptyList()
					)
					.build();
			
			ResponseEntity<OrderResponseDto> response = client.post().uri("/order").bodyValue(request).retrieve().toEntity(OrderResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(422, exceptionResponse.getRawStatusCode());
		}
		
		//TODO check repo
	}
	
	private void assertItem(ItemResponseDto actual, long expectedId, String expectedDescription, BigDecimal expectedPrice) {
		assertEquals(expectedId, actual.getId());
		assertTrue(actual.getDescription().equals(expectedDescription));
		assertTrue(actual.getPrice().compareTo(expectedPrice) == 0);
	}
	
}
