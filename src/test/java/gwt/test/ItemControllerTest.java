package gwt.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import gwt.dto.request.ItemRequestDto;
import gwt.dto.response.ItemResponseDto;
import gwt.entity.Item;
import gwt.repo.ItemRepo;

public class ItemControllerTest extends BaseTest {

	@Autowired
	private ItemRepo itemRepo;
	
	@AfterEach
	void teardown() {
		itemRepo.deleteAll();
	}
	
	@Test
	void getItems() {
		createItem(1L, "First Item", new BigDecimal("1.00"));
		createItem(2L, "Second Item", new BigDecimal("2.00"));
		createItem(3L, "Third Item", new BigDecimal("3.00"));
		
		WebClient client = WebClient.create("http://localhost:" + port);

		ResponseEntity<List<ItemResponseDto>> response = client.get().uri("/item").retrieve().toEntityList(ItemResponseDto.class).block();
		
		assertEquals(200, response.getStatusCodeValue());
		
		List<ItemResponseDto> result = response.getBody();

		assertEquals(3, result.size());
		
		assertItem(result.get(0), 1L, "First Item", new BigDecimal("1.00"));
		assertItem(result.get(1), 2L, "Second Item", new BigDecimal("2.00"));
		assertItem(result.get(2), 3L, "Third Item", new BigDecimal("3.00"));		
	}
	
	@Test
	void getItem_validSku() {
		createItem(1L, "First Item", new BigDecimal("1.00"));
		
		WebClient client = WebClient.create("http://localhost:" + port);
		
		ResponseEntity<ItemResponseDto> response = client.get().uri("/item/1").retrieve().toEntity(ItemResponseDto.class).block();
		
		assertEquals(200, response.getStatusCodeValue());
		
		assertItem(response.getBody(), 1L, "First Item", new BigDecimal("1.00"));
	}
	
	@Test
	void getItem_invalidSku() {
		WebClient client = WebClient.create("http://localhost:" + port);

		try {
			client.get().uri("/item/1").retrieve().toEntity(ItemResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(404, exceptionResponse.getRawStatusCode());
		}
	}
	
	@Test
	void createItem_happyPath() {
		WebClient client = WebClient.create("http://localhost:" + port);

		ItemRequestDto request = ItemRequestDto.builder()
									.sku(1L)
									.description("First Item")
									.price(new BigDecimal("1.00"))
									.build();
		ResponseEntity<ItemResponseDto> response = client.post().uri("/item").bodyValue(request).retrieve().toEntity(ItemResponseDto.class).block();
		
		assertEquals(201, response.getStatusCodeValue());
		
		assertItem(response.getBody(), 1L, "First Item", new BigDecimal("1.00"));
		assertItemExistsInRepo(1L, "First Item", new BigDecimal("1.00"));
	}
	
	@Test
	void createItem_negativePrice() {
		WebClient client = WebClient.create("http://localhost:" + port);

		ItemRequestDto request = ItemRequestDto.builder()
				.sku(1L)
				.description("First Item")
				.price(new BigDecimal("-1.00"))
				.build();

		
		try {
			client.post().uri("/item").bodyValue(request).retrieve().toEntity(ItemResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(422, exceptionResponse.getRawStatusCode());
		} finally {
			assertItemDoesNotExistInRepo(1L);
		}
	}

	@Test
	void createItem_negativeSku() {
		WebClient client = WebClient.create("http://localhost:" + port);

		ItemRequestDto request = ItemRequestDto.builder()
				.sku(-1L)
				.description("First Item")
				.price(new BigDecimal("1.00"))
				.build();

		
		try {
			client.post().uri("/item").bodyValue(request).retrieve().toEntity(ItemResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(422, exceptionResponse.getRawStatusCode());
		} finally {
			assertItemDoesNotExistInRepo(1L);
		}
	}
	
	@Test
	void createItem_duplicateSku() {
		createItem(1L, "First Item", new BigDecimal("1.00"));
		
		WebClient client = WebClient.create("http://localhost:" + port);

		ItemRequestDto request = ItemRequestDto.builder()
				.sku(1L)
				.description("Updated Item")
				.price(new BigDecimal("10.00"))
				.build();

		
		try {
			client.post().uri("/item").bodyValue(request).retrieve().toEntity(ItemResponseDto.class).block();
			fail("Should have not gotten proper response");
		} catch (WebClientResponseException exceptionResponse) {
			assertEquals(409, exceptionResponse.getRawStatusCode());
		} finally {
			assertItemExistsInRepo(1L, "First Item", new BigDecimal("1.00"));
		}
	}	
	
	
	private void createItem(long sku, String description, BigDecimal price) {
		itemRepo.save(Item.builder()
				.sku(sku)
				.description(description)
				.price(price)
				.build()
			);
	}
	
	private void assertItem(ItemResponseDto actual, long expectedSku, String expectedDescription, BigDecimal expectedPrice) {
		assertEquals(expectedSku, actual.getSku());
		assertTrue(actual.getDescription().equals(expectedDescription));
		assertTrue(actual.getPrice().compareTo(expectedPrice) == 0);
	}
	
	private void assertItemExistsInRepo(long expectedSku, String expectedDescription, BigDecimal expectedPrice) {
		Optional<Item> potentialItem = itemRepo.findById(expectedSku);
		
		assertTrue(potentialItem.isPresent());
		assertEquals(expectedSku, potentialItem.get().getSku());
		assertTrue(potentialItem.get().getDescription().equals(expectedDescription));
		assertTrue(potentialItem.get().getPrice().compareTo(expectedPrice) == 0);
	}
	
	private void assertItemDoesNotExistInRepo(long sku) {
		Optional<Item> potentialItem = itemRepo.findById(sku);
		assertTrue(potentialItem.isEmpty());
	}
}
