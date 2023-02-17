package gwt.test.chain.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;

import gwt.dto.request.OrderRequestDto;
import gwt.dto.response.OrderResponseDto;

public abstract class OrderControllerActions extends WhenActionChain {

	private static final String ORDERS_ENDPOINT = "/order";
	private static final String ORDER_BY_ID_ENDPOINT = ORDERS_ENDPOINT + "/{id}";

	public static WhenGettingAllOrdersAction whenGettingAllOrdersAction() {
		return new WhenGettingAllOrdersAction();
	}
	
	public static class WhenGettingAllOrdersAction extends WhenActionChain {
		private WhenGettingAllOrdersAction() { super(); }
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.get().uri(ORDERS_ENDPOINT).retrieve().toEntityList(OrderResponseDto.class).block();
		}
	}
	
	public static WhenGettingOrderAction whenGettingOrderAction() {
		return new WhenGettingOrderAction();
	}
	
	public static class WhenGettingOrderAction extends WhenActionChain {
		private WhenGettingOrderAction() { super(); } 
		
		private Long id;

		public WhenGettingOrderAction byId(Long id) {
			this.id = id;
			return this;
		}
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.get().uri(
					uriBuilder ->
						uriBuilder.path(ORDER_BY_ID_ENDPOINT)
						.build(id))
				.retrieve().toEntity(OrderResponseDto.class).block();
		}
		
	}
	
	public static WhenCreatingOrderAction whenCreatingOrderAction() {
		return new WhenCreatingOrderAction();
	}
	
	public static class WhenCreatingOrderAction extends WhenActionChain {

		private OrderRequestDto.OrderRequestDtoBuilder dtoBuilder;
		
		private WhenCreatingOrderAction() {
			super();
			
			dtoBuilder = OrderRequestDto.builder();
		}
		
		public WhenCreatingOrderAction withItemSkus(Long... skus) {
			dtoBuilder.itemSkus(Stream.of(skus).collect(Collectors.toList()));
			return this;
		}
		
		@Override
		protected ResponseEntity<?> sendRequest() {
			return client.post().uri(ORDERS_ENDPOINT).bodyValue(dtoBuilder.build()).retrieve().toEntity(OrderResponseDto.class).block();
		}
		
		
	}

	
}
