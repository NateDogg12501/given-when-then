package gwt.test.chain.given;

import java.math.BigDecimal;

import gwt.entity.Item;
import gwt.repo.ItemRepo;

public class ItemScenarios extends GivenScenarioChain {

	private ItemRepo itemRepo;
	
	private Item.ItemBuilder itemBuilder;
	
	public ItemScenarios() {
		super();
		
		itemRepo = this.getBean(ItemRepo.class);
		itemBuilder = Item.builder();
	}

	public ItemScenarios withSku(Long sku) {
		itemBuilder.sku(sku);
		return this;
	}
	
	public ItemScenarios withDescription(String description) {
		itemBuilder.description(description);
		return this;
	}
	
	public ItemScenarios withPrice(BigDecimal price) {
		itemBuilder.price(price);
		return this;
	}
	
	@Override
	protected GivenScenarioChain closeCurrentChain() {
		itemRepo.save(itemBuilder.build());
		return this;
	}
}
