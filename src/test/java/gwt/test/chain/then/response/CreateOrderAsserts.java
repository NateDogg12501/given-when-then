package gwt.test.chain.then.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;
import gwt.test.chain.then.ThenAssertChain;

public class CreateOrderAsserts extends ThenAssertChain {

	private OrderResponseDto responseOrder;
	
	public CreateOrderAsserts() {
		super();
		
		responseOrder = (OrderResponseDto) responseEntity.getBody();
	}
	
	public CreateOrderAsserts hasId(Long expectedId) {
		assertThat(responseOrder.getId()).as("Order ID does not match").isEqualTo(expectedId);
		return this;
	}
	
	public CreateOrderAsserts hasTotalPrice(BigDecimal expectedTotalPrice) {
		assertThat(responseOrder.getTotalPrice()).as("Total Price does not match").isEqualByComparingTo(expectedTotalPrice);
		return this;
	}
	
	public CreateOrder_ItemListAsserts hasItemList() {
		return new CreateOrder_ItemListAsserts(responseOrder.getItems());
	}	
	
	public class CreateOrder_ItemListAsserts extends CreateOrderAsserts {
		
		private List<ItemResponseDto> itemList;
		
		private CreateOrder_ItemListAsserts(List<ItemResponseDto> itemList) {
			super();
			
			this.itemList = itemList;
		}
		
		public CreateOrder_ItemListAsserts hasListSize(int expectedSize) {
			assertThat(itemList).as("CreateOrder Item List Size does not match").hasSize(expectedSize);
			return this;
		}
		
		public CreateOrder_ItemList_ItemAsserts hasItem() {
			return new CreateOrder_ItemList_ItemAsserts(itemList, 0);
		}
	}

	public class CreateOrder_ItemList_ItemAsserts extends CreateOrder_ItemListAsserts {

		private ItemResponseDto itemDto;
		
		private CreateOrder_ItemList_ItemAsserts(List<ItemResponseDto> itemList, int index) {
			super(itemList);
			
			this.itemDto = itemList.get(index);
		}
		
		public CreateOrder_ItemList_ItemAsserts hasSku(Long expectedSku) {
			assertThat(itemDto.getSku()).as("Item SKU does not match").isEqualTo(expectedSku);
			return this;
		}
		
		public CreateOrder_ItemList_ItemAsserts hasDescription(String expectedDescription) {
			assertThat(itemDto.getDescription()).as("Item Description does not match").isEqualTo(expectedDescription);
			return this;
		}
		
		public CreateOrder_ItemList_ItemAsserts hasPrice(BigDecimal expectedPrice) {
			assertThat(itemDto.getPrice()).as("Item Price does not match").isEqualByComparingTo(expectedPrice);
			return this;
		}
	}
	
}
