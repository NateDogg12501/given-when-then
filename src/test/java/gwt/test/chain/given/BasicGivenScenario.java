package gwt.test.chain.given;

import org.springframework.context.ApplicationContext;

public class BasicGivenScenario extends GivenScenarioChain {

	public BasicGivenScenario(ApplicationContext context) {
		super(context);
	}
	
	public GivenScenarioChain noScenarios() {
		return this;
	} 
	
	@Override
	protected GivenScenarioChain closeCurrentChain() {
		return this;
	}

}
