package gwt.test.chain.then.repository;

import gwt.test.chain.then.ThenAssertChain;

public class RepositoryAssertChain extends ThenAssertChain {

	public RepositoryAssertChain() {
		super();
	}
	
	public ItemRepoAssertChain item() {
		return new ItemRepoAssertChain();
	}
	
	public OrderRepoAssertChain order() {
		return new OrderRepoAssertChain();
	}
}
