package gwt.test.chain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import gwt.entity.Item;
import gwt.repo.ItemRepo;

public class ItemRepoStartingAsserts extends ThenAssertChain {

	private ItemRepo itemRepo;
	
	public ItemRepoStartingAsserts(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context, responseEntity, exception);
		
		itemRepo = this.getBean(ItemRepo.class);
	}
	
	public ItemRepoAsserts withSku(Long sku) {
		Optional<Item> potentialItem = itemRepo.findById(sku);
		return new ItemRepoAsserts(this.context, this.response, this.exception, potentialItem);
	}
	
	public class ItemRepoAsserts extends ThenAssertChain {
		
		private Optional<Item> potentialItem;
		
		ItemRepoAsserts(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception, Optional<Item> potentialItem) {
			super(context, responseEntity, exception);
			this.potentialItem = potentialItem;
		}
		
		public ItemRepoAsserts exists() {
			assertTrue(potentialItem.isPresent(), "Item does not actually exist");
			return this;
		}
		
		public ItemRepoAsserts doesNotExist() {
			assertTrue(potentialItem.isEmpty(), "Item actually exists");
			return this;
		}
		
		public ItemRepoAsserts hasSku(Long expectedSku) {
			assertEquals(expectedSku, potentialItem.get().getSku(),  "Item SKU does not match");
			return this;
		}
		
		public ItemRepoAsserts hasDescription(String expectedDescription) {
			assertTrue(expectedDescription.equals(potentialItem.get().getDescription()), String.format("Actual description [%s] does not match expected description [%s]", potentialItem.get().getDescription(), expectedDescription));
			return this;
		}
		
		public ItemRepoAsserts hasPrice(BigDecimal expectedPrice) {
			assertTrue(expectedPrice.compareTo(potentialItem.get().getPrice()) == 0, String.format("Actual price [%s] does not match expected price [%s]", potentialItem.get().getPrice(), expectedPrice));
			return this;
		}
		
	}

}
