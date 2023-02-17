package gwt.test.chain.when;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

import gwt.dto.request.ItemRequestDto;
import gwt.dto.response.ItemResponseDto;

public abstract class ItemControllerActions extends WhenActionChain {
		
	private static final String ITEMS_ENDPOINT = "/item";
	private static final String ITEM_BY_SKU_ENDPOINT = ITEMS_ENDPOINT + "/{sku}";
	
	public static WhenGettingAllItemsAction whenGettingAllItemsAction() {
		return new WhenGettingAllItemsAction();
	}
	
	public static class WhenGettingAllItemsAction extends WhenActionChain {
		private WhenGettingAllItemsAction() { super(); }
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.get().uri(ITEMS_ENDPOINT).retrieve().toEntityList(ItemResponseDto.class).block();
		}
	}
	
	public static WhenGettingItemAction whenGettingItemAction() {
		return new WhenGettingItemAction();
	}
	
	public static class WhenGettingItemAction extends WhenActionChain {
		private WhenGettingItemAction() { super(); } 
		
		private Long sku;

		public WhenGettingItemAction bySku(Long sku) {
			this.sku = sku;
			return this;
		}
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.get().uri(
					uriBuilder ->
						uriBuilder.path(ITEM_BY_SKU_ENDPOINT)
						.build(sku))
				.retrieve().toEntity(ItemResponseDto.class).block();
		}
		
	}
	
	public static WhenCreatingItemAction whenCreatingItemAction() {
		return new WhenCreatingItemAction();
	}
	
	public static class WhenCreatingItemAction extends WhenActionChain {

		private ItemRequestDto.ItemRequestDtoBuilder dtoBuilder;
		
		private WhenCreatingItemAction() {
			super();
			
			dtoBuilder = ItemRequestDto.builder();
		}
		
		public WhenCreatingItemAction withSku(Long sku) {
			dtoBuilder.sku(sku);
			return this;
		}
		
		public WhenCreatingItemAction withDescription(String description) {
			dtoBuilder.description(description);
			return this;
		}
		
		public WhenCreatingItemAction withPrice(BigDecimal price) {
			dtoBuilder.price(price);
			return this;
		}
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.post().uri(ITEMS_ENDPOINT).bodyValue(dtoBuilder.build()).retrieve().toEntity(ItemResponseDto.class).block();
		}
		
		
	}

}
