package gwt.test.chain;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

public class ResponseBodyAssertChain extends ThenAssertChain {

	public ResponseBodyAssertChain(ApplicationContext context, ResponseEntity<?> responseEntity, Exception exception) {
		super(context, responseEntity, exception);
	}
	
	public ItemResponseDtoListAsserts hasItemResponseDtoList() {
		return new ItemResponseDtoListAsserts(this.context, this.response, this.exception);
	}
	
	public ItemResponseDtoAsserts hasItemResponseDto() {
		return new ItemResponseDtoAsserts(this.context, this.response, this.exception);
	}

}
