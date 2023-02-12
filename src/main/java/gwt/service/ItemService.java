package gwt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gwt.entity.Item;
import gwt.exception.DuplicateSkuExists;
import gwt.repo.ItemRepo;

@Service
public class ItemService {

	private ItemRepo itemRepo;
	
	@Autowired
	public ItemService(ItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}
	
	public List<Item> getAllItems() {
		return itemRepo.findAll();
	}
	
	public Optional<Item> getItemBySku(Long sku) {
		return itemRepo.findById(sku);
	}
	
	public Item createItem(Long sku, String description, BigDecimal price) {
		if (sku == null || sku < 0 || price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Item values are not valid");
		}
		
		if (getItemBySku(sku).isPresent()) {
			throw new DuplicateSkuExists("Sku [" + sku + "] already exists");
		}
		
		Item newItem = Item.builder()
						.sku(sku)
						.description(description)
						.price(price)
						.build();
		
		return itemRepo.save(newItem);
	}
}
