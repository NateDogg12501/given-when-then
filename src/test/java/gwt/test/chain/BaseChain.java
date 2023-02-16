package gwt.test.chain;

import org.springframework.context.ApplicationContext;

public abstract class BaseChain {

	protected static ApplicationContext applicationContext;
	
	public BaseChain(ApplicationContext context) {
		applicationContext = context;
	}
	
	protected BaseChain() {
		
	}
	
	protected <T> T getBean(Class<T> beanType) {
		return applicationContext.getBean(beanType);
	}
	
}
