package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import gwt.dto.response.ItemResponseDto;

public class GetAllItemsAsserts extends ThenAssertChain {

	private List<ItemResponseDto> responseItemList;
	
	public GetAllItemsAsserts() {
		super();
		
		responseItemList = (List<ItemResponseDto>) responseEntity.getBody();
	}
	
	public GetAllItemsAsserts hasListSize(int expectedSize) {
		assertThat(responseItemList).as("ItemResponseDtoListAsserts list size does not match").hasSize(expectedSize);
		return this;
	}
	
	public GetAllItems_ItemAsserts hasFirstItem() {
		return new GetAllItems_ItemAsserts(responseItemList.get(0));
	}
	
	public GetAllItems_ItemAsserts hasSecondItem() {
		return new GetAllItems_ItemAsserts(responseItemList.get(1));
	}
	
	public GetAllItems_ItemAsserts hasThirdItem() {
		return new GetAllItems_ItemAsserts(responseItemList.get(2));
	}
	
	public class GetAllItems_ItemAsserts extends GetAllItemsAsserts {

		private ItemResponseDto itemDto;
		
		private GetAllItems_ItemAsserts(ItemResponseDto itemDto) {
			super();
			
			this.itemDto = itemDto;
		}
		
		public GetAllItems_ItemAsserts hasSku(Long expectedSku) {
			assertThat(itemDto.getSku()).as("Item SKU does not match").isEqualTo(expectedSku);
			return this;
		}
		
		public GetAllItems_ItemAsserts hasDescription(String expectedDescription) {
			assertThat(itemDto.getDescription()).as("Item Description does not match").isEqualTo(expectedDescription);
			return this;
		}
		
		public GetAllItems_ItemAsserts hasPrice(BigDecimal expectedPrice) {
			assertThat(itemDto.getPrice()).as("Item Price does not match").isEqualByComparingTo(expectedPrice);
			return this;
		}
	}
}
