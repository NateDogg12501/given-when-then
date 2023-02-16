package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import gwt.dto.response.ItemResponseDto;

public class ItemResponseDtoAsserts extends ThenAssertChain {

	private ItemResponseDto responseItem;
	
	public ItemResponseDtoAsserts() {
		super();
		
		responseItem = (ItemResponseDto) responseEntity.getBody();
	}

	public ItemResponseDtoAsserts hasSku(Long expectedSku) {
		assertThat(responseItem.getSku()).as("Item SKU does not match").isEqualTo(expectedSku);
		return this;
	}
	
	public ItemResponseDtoAsserts hasDescription(String expectedDescription) {
		assertThat(responseItem.getDescription()).as("Item Description does not match").isEqualTo(expectedDescription);
		return this;
	}
	
	public ItemResponseDtoAsserts hasPrice(BigDecimal expectedPrice) {
		assertThat(responseItem.getPrice()).as("Item Price does not match").isEqualTo(expectedPrice);
		return this;
	}
	
}
