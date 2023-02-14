package gwt.test.chain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import gwt.dto.response.ItemResponseDto;

public class ItemResponseDtoAsserts extends ThenAssertChain {

	private ItemResponseDto responseItem;
	
	public ItemResponseDtoAsserts(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context, responseEntity, exception);
		responseItem = (ItemResponseDto) responseEntity.getBody();
	}

	public ItemResponseDtoAsserts hasSku(Long expectedSku) {
		assertEquals(expectedSku, responseItem.getSku());
		return this;
	}
	
	public ItemResponseDtoAsserts hasDescription(String expectedDescription) {
		assertTrue(expectedDescription.equals(responseItem.getDescription()));
		return this;
	}
	
	public ItemResponseDtoAsserts hasPrice(BigDecimal expectedPrice) {
		assertTrue(expectedPrice.compareTo(responseItem.getPrice()) == 0);
		return this;
	}
	
}
