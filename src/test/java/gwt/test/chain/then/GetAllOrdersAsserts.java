package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;

public class GetAllOrdersAsserts extends ThenAssertChain {

	private List<OrderResponseDto> responseOrderList;
	
	public GetAllOrdersAsserts() {
		super();
		
		responseOrderList = (List<OrderResponseDto>) responseEntity.getBody();
	}
	
	public GetAllOrdersAsserts hasListSize(int expectedSize) {
		assertThat(responseOrderList).as("GetAllOrders list size does not match").hasSize(expectedSize);
		return this;
	}
	
	public GetAllOrders_OrderAsserts hasFirstOrder() {
		return new GetAllOrders_OrderAsserts(responseOrderList.get(0));
	}
	
	public GetAllOrders_OrderAsserts hasSecondOrder() {
		return new GetAllOrders_OrderAsserts(responseOrderList.get(1));
	}
		
	public class GetAllOrders_OrderAsserts extends GetAllOrdersAsserts {

		private OrderResponseDto orderDto;
		
		private GetAllOrders_OrderAsserts(OrderResponseDto orderDto) {
			super();
			
			this.orderDto = orderDto;
		}
		
		public GetAllOrders_OrderAsserts hasId(Long expectedId) {
			assertThat(orderDto.getId()).as("Order ID does not match").isEqualTo(expectedId);
			return this;
		}
		
		public GetAllOrders_OrderAsserts hasTotalPrice(BigDecimal expectedTotalPrice) {
			assertThat(orderDto.getTotalPrice()).as("Total Price does not match").isEqualByComparingTo(expectedTotalPrice);
			return this;
		}
		
		public GetAllOrders_Order_ItemListAsserts hasItemList() {
			return new GetAllOrders_Order_ItemListAsserts(orderDto.getItems());
		}	
	}
	
	public class GetAllOrders_Order_ItemListAsserts extends GetAllOrdersAsserts {
		
		private List<ItemResponseDto> itemList;
		
		private GetAllOrders_Order_ItemListAsserts(List<ItemResponseDto> itemList) {
			super();
			
			this.itemList = itemList;
		}
		
		public GetAllOrders_Order_ItemListAsserts hasListSize(int expectedSize) {
			assertThat(itemList).as("GetAllOrders Item List Size does not match").hasSize(expectedSize);
			return this;
		}
		
		public GetAllOrders_Order_ItemList_ItemAsserts hasFirstItem() {
			return new GetAllOrders_Order_ItemList_ItemAsserts(itemList, 0);
		}
		
		public GetAllOrders_Order_ItemList_ItemAsserts hasSecondItem() {
			return new GetAllOrders_Order_ItemList_ItemAsserts(itemList, 1);
		}
		
		public GetAllOrders_Order_ItemList_ItemAsserts hasThirdItem() {
			return new GetAllOrders_Order_ItemList_ItemAsserts(itemList, 2);
		}
	}
	
	public class GetAllOrders_Order_ItemList_ItemAsserts extends GetAllOrders_Order_ItemListAsserts {

		private ItemResponseDto itemDto;
		
		private GetAllOrders_Order_ItemList_ItemAsserts(List<ItemResponseDto> itemList, int index) {
			super(itemList);
			
			this.itemDto = itemList.get(index);
		}
		
		public GetAllOrders_Order_ItemList_ItemAsserts hasSku(Long expectedSku) {
			assertThat(itemDto.getSku()).as("Item SKU does not match").isEqualTo(expectedSku);
			return this;
		}
		
		public GetAllOrders_Order_ItemList_ItemAsserts hasDescription(String expectedDescription) {
			assertThat(itemDto.getDescription()).as("Item Description does not match").isEqualTo(expectedDescription);
			return this;
		}
		
		public GetAllOrders_Order_ItemList_ItemAsserts hasPrice(BigDecimal expectedPrice) {
			assertThat(itemDto.getPrice()).as("Item Price does not match").isEqualByComparingTo(expectedPrice);
			return this;
		}
	}
	
}
