package gwt.test.chain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import gwt.dto.response.ItemResponseDto;

public class ItemResponseDtoListAsserts extends ThenAssertChain {

	private List<ItemResponseDto> responseItemList;
	
	public ItemResponseDtoListAsserts(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context, responseEntity, exception);
		responseItemList = (List<ItemResponseDto>) responseEntity.getBody();
	}
	
	public ItemResponseDtoListAsserts withListSize(int expectedSize) {
		assertEquals(expectedSize, responseItemList.size(), "ItemRespnseDtoListAsserts list size does not match");
		return this;
	}
	
	public ItemResponseDtoListIndexAsserts withFirstItem() {
		return new ItemResponseDtoListIndexAsserts(this.context, this.response, this.exception, responseItemList.get(0));
	}
	
	public ItemResponseDtoListIndexAsserts withSecondItem() {
		return new ItemResponseDtoListIndexAsserts(this.context, this.response, this.exception, responseItemList.get(1));
	}
	
	public ItemResponseDtoListIndexAsserts withThirdItem() {
		return new ItemResponseDtoListIndexAsserts(this.context, this.response, this.exception, responseItemList.get(2));
	}
	
	public class ItemResponseDtoListIndexAsserts extends ItemResponseDtoListAsserts {

		private ItemResponseDto itemDto;
		
		public ItemResponseDtoListIndexAsserts(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception, ItemResponseDto itemDto) {
			super(context, responseEntity, exception);
			
			this.itemDto = itemDto;
		}
		
		public ItemResponseDtoListIndexAsserts hasSku(Long expectedSku) {
			assertEquals(expectedSku, itemDto.getSku(), "Item SKU does not match");
			return this;
		}
		
		public ItemResponseDtoListIndexAsserts hasDescription(String expectedDescription) {
			assertTrue(expectedDescription.equals(itemDto.getDescription()), String.format("Actual description [%s] does not match expected description [%s]", itemDto.getDescription(), expectedDescription));
			return this;
		}
		
		public ItemResponseDtoListIndexAsserts hasPrice(BigDecimal expectedPrice) {
			assertTrue(expectedPrice.compareTo(itemDto.getPrice()) == 0, String.format("Actual price [%s] does not match expected price [%s]", itemDto.getPrice(), expectedPrice));
			return this;
		}
	}
}
