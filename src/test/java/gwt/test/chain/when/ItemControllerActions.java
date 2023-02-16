package gwt.test.chain.when;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

import gwt.dto.request.ItemRequestDto;
import gwt.dto.response.ItemResponseDto;

public abstract class ItemControllerActions extends WhenActionChain {
		
	private static final String ITEM_ENDPOINT = "/item";
	private static final String ITEM_BY_SKU_ENDPOINT = ITEM_ENDPOINT + "/{sku}";
	
	public static WhenGettingAllItemsChain whenGettingAllItemsChain() {
		return new WhenGettingAllItemsChain();
	}
	
	public static class WhenGettingAllItemsChain extends WhenActionChain {
		private WhenGettingAllItemsChain() { super(); }
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.get().uri(ITEM_ENDPOINT).retrieve().toEntityList(ItemResponseDto.class).block();
		}
	}
	
	public static WhenGettingItemChain whenGettingItemChain() {
		return new WhenGettingItemChain();
	}
	
	public static class WhenGettingItemChain extends WhenActionChain {
		private WhenGettingItemChain() { super(); } 
		
		private Long sku;

		public WhenGettingItemChain bySku(Long sku) {
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
	
	public static WhenCreatingItemChain whenCreatingItemChain() {
		return new WhenCreatingItemChain();
	}
	
	public static class WhenCreatingItemChain extends WhenActionChain {

		private ItemRequestDto.ItemRequestDtoBuilder dtoBuilder;
		
		private WhenCreatingItemChain() {
			super();
			
			dtoBuilder = ItemRequestDto.builder();
		}
		
		public WhenCreatingItemChain withSku(Long sku) {
			dtoBuilder.sku(sku);
			return this;
		}
		
		public WhenCreatingItemChain withDescription(String description) {
			dtoBuilder.description(description);
			return this;
		}
		
		public WhenCreatingItemChain withPrice(BigDecimal price) {
			dtoBuilder.price(price);
			return this;
		}
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.post().uri(ITEM_ENDPOINT).bodyValue(dtoBuilder.build()).retrieve().toEntity(ItemResponseDto.class).block();
		}
		
		
	}

}
