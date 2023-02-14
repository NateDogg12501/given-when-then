package gwt.test.chain;

import org.springframework.context.ApplicationContext;

public class GivenScenarioChain extends BaseChain {

	public GivenScenarioChain(ApplicationContext context) {
		super(context);
	}

	public GivenScenarioChain noScenarios() {
		return this;
	}
	
	public ItemScenarios existingItem() {
		closeCurrentChain();
		return new ItemScenarios(this.context);
	}
	
	public WhenActionChain when() {
		closeCurrentChain();
		return new WhenActionChain(this.context);
	}
	
	protected GivenScenarioChain closeCurrentChain() {
		return this;
	}
	
}
