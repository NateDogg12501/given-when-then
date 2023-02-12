package gwt.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gwt.dto.request.ItemRequestDto;
import gwt.dto.request.OrderRequestDto;
import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;
import gwt.entity.Item;
import gwt.entity.Order;
import gwt.exception.DuplicateSkuExists;
import gwt.exception.NoItemExists;
import gwt.service.ItemService;
import gwt.service.OrderService;

@RestController
@RequestMapping("/item")
public class ItemController {

	private ItemService itemService;
	
	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping
	public ResponseEntity<List<ItemResponseDto>> getItems() {
		List<ItemResponseDto> items = new ArrayList<>();
		
		for (Item item : itemService.getAllItems()) {
			items.add(mapEntityToDto(item));
		}
		
		return ResponseEntity.ok(items);
	}
	
	@GetMapping("{sku}")
	public ResponseEntity<ItemResponseDto> getItemBySku(@PathVariable Long sku) {
		Optional<Item> item = itemService.getItemBySku(sku);
		
		if (item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.ok(mapEntityToDto(item.get()));
	}
	
	@PostMapping
	public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemRequestDto itemRequestDto) {
		try {
			Item newItem = itemService.createItem(itemRequestDto.getSku(), itemRequestDto.getDescription(), itemRequestDto.getPrice());
			return ResponseEntity.status(HttpStatus.CREATED).body(mapEntityToDto(newItem));
		} catch (IllegalArgumentException iae) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		} catch (DuplicateSkuExists dse) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
	}

	private ItemResponseDto mapEntityToDto(Item item) {
		return ItemResponseDto.builder()
				.sku(item.getSku())
				.description(item.getDescription())
				.price(item.getPrice())
				.build();
	}
	
}
