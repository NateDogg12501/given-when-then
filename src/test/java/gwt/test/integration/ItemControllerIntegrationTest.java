package gwt.test.integration;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ItemControllerIntegrationTest extends BaseIntegrationTest {
	
	@Test
	void getItems() {
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
		.when()
			.gettingAllItems()
		.then()
			.responseStatus()
				.isOk()
			.responseBody()
				.hasGetAllItemsResponseBody()
					.hasListSize(3)
					.hasFirstItem()
						.hasSku(1L)
						.hasDescription("First Item")
						.hasPrice(new BigDecimal("1.00"))
					.hasSecondItem()
						.hasSku(2L)
						.hasDescription("Second Item")
						.hasPrice(new BigDecimal("2.00"))
					.hasThirdItem()
						.hasSku(3L)
						.hasDescription("Third Item")
						.hasPrice(new BigDecimal("3.00"))
		;	
	}
	
	@Test
	void getItem_validSku() {
		given()
			.existingItem()
				.withSku(1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("1.00"))
		.when()
			.gettingItem()
				.bySku(1L)
		.then()
			.responseStatus()
				.isOk()
			.responseBody()
				.hasGetItemBySkuResponseBody()
					.hasSku(1L)
					.hasDescription("First Item")
					.hasPrice(new BigDecimal("1.00"))
				
		;	
	}
	
	@Test
	void getItem_invalidSku() {
		given()
			.noScenarios()
		.when()
			.gettingItem()
				.bySku(1L)
		.then()
			.responseStatus()
				.isNotFound();
	}
	
	@Test
	void createItem_happyPath() {
		given()
			.noScenarios()
		.when()
			.creatingItem()
				.withSku(1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("1.00"))
		.then()
			.responseStatus()
				.isCreated()
			.responseBody()
				.hasCreateItemResponseBody()
					.hasSku(1L)
					.hasDescription("First Item")
					.hasPrice(new BigDecimal("1.00"))
			.repository()
				.item()
					.withSku(1L)
						.exists()
						.hasSku(1L)
						.hasDescription("First Item")
						.hasPrice(new BigDecimal("1.00"))
		;
	}
	
	@Test
	void createItem_negativePrice() {
		given()
			.noScenarios()
		.when()
			.creatingItem()
				.withSku(1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("-1.00"))
		.then()
			.responseStatus()
				.isUnprocessableEntity()
			.repository()
				.item()
					.withSku(1L)
						.doesNotExist();
	}

	@Test
	void createItem_negativeSku() {
		given()
			.noScenarios()
		.when()
			.creatingItem()
				.withSku(-1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("1.00"))
		.then()
			.responseStatus()
				.isUnprocessableEntity()
			.repository()
				.item()
					.withSku(-1L)
						.doesNotExist();
	}
	
	@Test
	void createItem_duplicateSku() {
		given()
			.existingItem()
				.withSku(1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("1.00"))
		.when()
			.creatingItem()
				.withSku(1L)
				.withDescription("Updated Item")
				.withPrice(new BigDecimal("10.00"))
		.then()
			.responseStatus()
				.isConflict()
			.repository()
				.item()
					.withSku(1L)
						.exists()
						.hasDescription("First Item")
						.hasPrice(new BigDecimal("1.00"))
		;
	}	
	
}
