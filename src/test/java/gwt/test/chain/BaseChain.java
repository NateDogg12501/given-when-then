package gwt.test.chain;

import org.springframework.context.ApplicationContext;

public class BaseChain {

	protected ApplicationContext context;
	
	public BaseChain(ApplicationContext context) {
		this.context = context;
	}
	
	protected <T> T getBean(Class<T> beanType) {
		return context.getBean(beanType);
	}
	
}
