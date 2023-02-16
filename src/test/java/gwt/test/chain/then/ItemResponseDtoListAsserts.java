package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import gwt.dto.response.ItemResponseDto;

public class ItemResponseDtoListAsserts extends ThenAssertChain {

	private List<ItemResponseDto> responseItemList;
	
	public ItemResponseDtoListAsserts() {
		super();
		
		responseItemList = (List<ItemResponseDto>) responseEntity.getBody();
	}
	
	public ItemResponseDtoListAsserts withListSize(int expectedSize) {
		assertThat(responseItemList).as("ItemRespnseDtoListAsserts list size does not match").hasSize(expectedSize);
		return this;
	}
	
	public ItemResponseDtoListIndexAsserts withFirstItem() {
		return new ItemResponseDtoListIndexAsserts(responseItemList.get(0));
	}
	
	public ItemResponseDtoListIndexAsserts withSecondItem() {
		return new ItemResponseDtoListIndexAsserts(responseItemList.get(1));
	}
	
	public ItemResponseDtoListIndexAsserts withThirdItem() {
		return new ItemResponseDtoListIndexAsserts(responseItemList.get(2));
	}
	
	public class ItemResponseDtoListIndexAsserts extends ItemResponseDtoListAsserts {

		private ItemResponseDto itemDto;
		
		private ItemResponseDtoListIndexAsserts(ItemResponseDto itemDto) {
			super();
			
			this.itemDto = itemDto;
		}
		
		public ItemResponseDtoListIndexAsserts hasSku(Long expectedSku) {
			assertThat(itemDto.getSku()).as("Item SKU does not match").isEqualTo(expectedSku);
			return this;
		}
		
		public ItemResponseDtoListIndexAsserts hasDescription(String expectedDescription) {
			assertThat(itemDto.getDescription()).as("Item Description does not match").isEqualTo(expectedDescription);
			return this;
		}
		
		public ItemResponseDtoListIndexAsserts hasPrice(BigDecimal expectedPrice) {
			assertThat(itemDto.getPrice()).as("Item Price does not match").isEqualByComparingTo(expectedPrice);
			return this;
		}
	}
}
