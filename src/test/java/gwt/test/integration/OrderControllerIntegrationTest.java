package gwt.test.integration;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;


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
			.gettingAllOrders()
		.then()
			.responseStatus()
				.isOk()
			.responseBody()
				.hasGetAllOrdersResponseBody()
					.hasListSize(2)
					.hasFirstOrder()
						.hasId(1L)
						.hasTotalPrice(new BigDecimal("6.00"))
						.hasItemList()
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
					.hasSecondOrder()
						.hasId(2L)
						.hasTotalPrice(new BigDecimal("3.00"))
						.hasItemList()
							.hasListSize(2)
								.hasFirstItem()
									.hasSku(1L)
									.hasDescription("First Item")
									.hasPrice(new BigDecimal("1.00"))
								.hasSecondItem()
									.hasSku(2L)
									.hasDescription("Second Item")
									.hasPrice(new BigDecimal("2.00"))
		;
	}
	
	@Test
	void getOrder_happyPath() {
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
			.when()
				.gettingOrder()
					.byId(1L)
			.then()
				.responseStatus()
					.isOk()
				.responseBody()
					.hasGetOrderResponseBody()
						.hasId(1L)
						.hasTotalPrice(new BigDecimal("6.00"))
						.hasItemList()
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
	void getOrder_invalidId() {
		given()
			.noScenarios()
		.when()
			.gettingOrder()
				.byId(1L)
		.then()
			.responseStatus()
				.isNotFound()
		;
	}
	
	@Test
	void createOrder_happyPath() {
		given()
			.existingItem()
				.withSku(1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("1.00"))
			.nextOrderCreated()
				.willHaveId(1L)
		.when()
			.creatingOrder()
				.withItemSkus(1L)
		.then()
			.responseStatus()
				.isCreated()
			.responseBody()
				.hasCreateOrderResponseBody()
					.hasId(1L)
					.hasTotalPrice(new BigDecimal("1.00"))
					.hasItemList()
						.hasListSize(1)
						.hasItem()
							.hasSku(1L)
							.hasDescription("First Item")
							.hasPrice(new BigDecimal("1.00"))
			.repository()
				.order()
					.withId(1L)
						.exists()
						.hasId(1L)
						.hasTotalPrice(new BigDecimal("1.00"))
						.hasNumberOfItems(1)
						.hasItemSkus(1L)
		;
	}
	
	@Test
	void createOrder_invalidItemSku() {
		given()
			.noScenarios()
		.when()
			.creatingOrder()
				.withItemSkus(1L)
		.then()
			.responseStatus()
				.isUnprocessableEntity()
			.repository()
				.order()
					.noOrdersExist();
	}
	
	@Test
	void createOrder_noItems() {
		given()
			.existingItem()
				.withSku(1L)
				.withDescription("First Item")
				.withPrice(new BigDecimal("1.00"))
		.when()
			.creatingOrder()
				.withItemSkus()
		.then()
			.responseStatus()
				.isUnprocessableEntity()
			.repository()
				.order()
					.noOrdersExist();
	}
}
