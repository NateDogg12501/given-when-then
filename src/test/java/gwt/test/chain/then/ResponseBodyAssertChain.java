package gwt.test.chain.then;

public class ResponseBodyAssertChain extends ThenAssertChain {

	public ResponseBodyAssertChain() {
		super();
	}
	
	public ItemResponseDtoListAsserts hasItemResponseDtoList() {
		return new ItemResponseDtoListAsserts();
	}
	
	public ItemResponseDtoAsserts hasItemResponseDto() {
		return new ItemResponseDtoAsserts();
	}

}
