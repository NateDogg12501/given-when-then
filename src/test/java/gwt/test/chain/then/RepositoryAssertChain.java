package gwt.test.chain.then;

public class RepositoryAssertChain extends ThenAssertChain {

	public RepositoryAssertChain() {
		super();
	}
	
	public ItemRepoAssertChain item() {
		return new ItemRepoAssertChain();
	}
}
