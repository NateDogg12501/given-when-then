package gwt.test.chain.given;

import org.springframework.context.ApplicationContext;

import gwt.test.chain.BaseChain;
import gwt.test.chain.when.BasicWhenAction;

public abstract class GivenScenarioChain extends BaseChain {

	protected GivenScenarioChain() {
		super();
	}
	
	public GivenScenarioChain(ApplicationContext context) {
		super(context);
	}
	
	public BasicWhenAction when() {
		closeCurrentChain();
		return new BasicWhenAction(applicationContext);
	}
	
	public ItemScenarios existingItem() {
		closeCurrentChain();
		return new ItemScenarios();
	}
	
	protected abstract GivenScenarioChain closeCurrentChain();
	
}
