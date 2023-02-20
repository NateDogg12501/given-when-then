package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;

public class GetOrderAsserts extends ThenAssertChain {

	private OrderResponseDto responseOrder;
	
	public GetOrderAsserts() {
		super();
		
		responseOrder = (OrderResponseDto) responseEntity.getBody();
	}
	
	public GetOrderAsserts hasId(Long expectedId) {
		assertThat(responseOrder.getId()).as("Order ID does not match").isEqualTo(expectedId);
		return this;
	}
	
	public GetOrderAsserts hasTotalPrice(BigDecimal expectedTotalPrice) {
		assertThat(responseOrder.getTotalPrice()).as("Total Price does not match").isEqualByComparingTo(expectedTotalPrice);
		return this;
	}
	
	public GetOrder_ItemListAsserts hasItemList() {
		return new GetOrder_ItemListAsserts(responseOrder.getItems());
	}	
	
	public class GetOrder_ItemListAsserts extends GetOrderAsserts {
		
		private List<ItemResponseDto> itemList;
		
		private GetOrder_ItemListAsserts(List<ItemResponseDto> itemList) {
			super();
			
			this.itemList = itemList;
		}
		
		public GetOrder_ItemListAsserts hasListSize(int expectedSize) {
			assertThat(itemList).as("GetOrder Item List Size does not match").hasSize(expectedSize);
			return this;
		}
		
		public GetOrder_ItemList_ItemAsserts hasFirstItem() {
			return new GetOrder_ItemList_ItemAsserts(itemList, 0);
		}
		
		public GetOrder_ItemList_ItemAsserts hasSecondItem() {
			return new GetOrder_ItemList_ItemAsserts(itemList, 1);
		}
		
		public GetOrder_ItemList_ItemAsserts hasThirdItem() {
			return new GetOrder_ItemList_ItemAsserts(itemList, 2);
		}
	}

	public class GetOrder_ItemList_ItemAsserts extends GetOrder_ItemListAsserts {

		private ItemResponseDto itemDto;
		
		private GetOrder_ItemList_ItemAsserts(List<ItemResponseDto> itemList, int index) {
			super(itemList);
			
			this.itemDto = itemList.get(index);
		}
		
		public GetOrder_ItemList_ItemAsserts hasSku(Long expectedSku) {
			assertThat(itemDto.getSku()).as("Item SKU does not match").isEqualTo(expectedSku);
			return this;
		}
		
		public GetOrder_ItemList_ItemAsserts hasDescription(String expectedDescription) {
			assertThat(itemDto.getDescription()).as("Item Description does not match").isEqualTo(expectedDescription);
			return this;
		}
		
		public GetOrder_ItemList_ItemAsserts hasPrice(BigDecimal expectedPrice) {
			assertThat(itemDto.getPrice()).as("Item Price does not match").isEqualByComparingTo(expectedPrice);
			return this;
		}
	}
}