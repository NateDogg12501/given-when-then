package gwt.test.chain.then;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import gwt.dto.response.ItemResponseDto;
import gwt.dto.response.OrderResponseDto;
import gwt.test.chain.then.ItemResponseDtoListAsserts.ItemResponseDtoListIndexAsserts;

public class OrderResponseDtoListAsserts extends ThenAssertChain {

	private List<OrderResponseDto> responseOrderList;
	
	public OrderResponseDtoListAsserts() {
		super();
		
		responseOrderList = (List<OrderResponseDto>) responseEntity.getBody();
	}
	
	public OrderResponseDtoListAsserts withListSize(int expectedSize) {
		assertThat(responseOrderList).as("OrderResponseDtoListAsserts list size does not match").hasSize(expectedSize);
		return this;
	}
	
	public OrderResponseDtoListIndexAsserts withFirstOrder() {
		return new OrderResponseDtoListIndexAsserts(responseOrderList.get(0));
	}
	
	public OrderResponseDtoListIndexAsserts withSecondOrder() {
		return new OrderResponseDtoListIndexAsserts(responseOrderList.get(1));
	}
		
	public class OrderResponseDtoListIndexAsserts extends OrderResponseDtoListAsserts {

		private OrderResponseDto orderDto;
		
		private OrderResponseDtoListIndexAsserts(OrderResponseDto orderDto) {
			super();
			
			this.orderDto = orderDto;
		}
		
		public OrderResponseDtoListIndexAsserts hasId(Long expectedId) {
			assertThat(orderDto.getId()).as("Order ID does not match").isEqualTo(expectedId);
			return this;
		}
		
		public OrderResponseDtoListIndexAsserts hasTotalPrice(BigDecimal expectedTotalPrice) {
			assertThat(orderDto.getTotalPrice()).as("Total Price does not match").isEqualByComparingTo(expectedTotalPrice);
			return this;
		}
		
		public OrderResponseDtoListIndexAsserts hasItemList() {
			assertThat(orderDto).equals("BLAH");
			return this;
		}
		
	}
	
}
