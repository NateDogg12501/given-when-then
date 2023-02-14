package gwt.test.chain;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import gwt.dto.request.ItemRequestDto;
import gwt.dto.response.ItemResponseDto;

public class ItemControllerActions extends WhenActionChain {

	private WebClient client;
	
	public ItemControllerActions(ApplicationContext context) {
		super(context);

		String port = context.getEnvironment().getProperty("local.server.port");
		client = WebClient.create("http://localhost:" + port);
	}
		
	public class WhenGettingAllItemsChain extends WhenActionChain {
		
		public WhenGettingAllItemsChain(ApplicationContext context) {
			super(context);
		}

		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.get().uri("/item").retrieve().toEntityList(ItemResponseDto.class).block();
		}
	}
	
	public class WhenGettingItemChain extends WhenActionChain {

		private Long sku;
		
		public WhenGettingItemChain(ApplicationContext context) {
			super(context);
		}
		
		public WhenGettingItemChain bySku(Long sku) {
			this.sku = sku;
			return this;
		}
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.get().uri("/item/" + sku).retrieve().toEntity(ItemResponseDto.class).block();
		}
		
	}
	
	public class WhenCreatingItemChain extends WhenActionChain {

		private ItemRequestDto.ItemRequestDtoBuilder dtoBuilder;
		
		public WhenCreatingItemChain(ApplicationContext context) {
			super(context);
			
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
			return client.post().uri("/item").bodyValue(dtoBuilder.build()).retrieve().toEntity(ItemResponseDto.class).block();
		}
		
		
	}

}
