package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import gwt.entity.Item;
import gwt.repo.ItemRepo;

public class ItemRepoAssertChain extends ThenAssertChain {

	private ItemRepo itemRepo;
	
	public ItemRepoAssertChain() {
		super();
		
		itemRepo = this.getBean(ItemRepo.class);
	}
	
	public ItemRepoAsserts withSku(Long sku) {
		Optional<Item> potentialItem = itemRepo.findById(sku);
		return new ItemRepoAsserts(potentialItem);
	}
	
	public class ItemRepoAsserts extends ThenAssertChain {
		
		private Optional<Item> potentialItem;
		
		private ItemRepoAsserts(Optional<Item> potentialItem) {
			super();
			
			this.potentialItem = potentialItem;
		}
		
		public ItemRepoAsserts exists() {
			assertThat(potentialItem).overridingErrorMessage("Item does not actually exist").isPresent();
			return this;
		}
		
		public ItemRepoAsserts doesNotExist() {
			assertThat(potentialItem).overridingErrorMessage("Item actually does exist").isEmpty();
			return this;
		}
		
		public ItemRepoAsserts hasSku(Long expectedSku) {
			assertThat(potentialItem.get().getSku()).as("Item SKU does not match").isEqualTo(expectedSku);
			return this;
		}
		
		public ItemRepoAsserts hasDescription(String expectedDescription) {
			assertThat(potentialItem.get().getDescription()).as("Item Description does not match").isEqualTo(expectedDescription);
			return this;
		}
		
		public ItemRepoAsserts hasPrice(BigDecimal expectedPrice) {
			assertThat(potentialItem.get().getPrice()).as("Item Price does not match").isEqualTo(expectedPrice);
			return this;
		}
		
	}

}
