package gwt.test.integration;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;

import gwt.repo.ItemRepo;
import gwt.repo.OrderRepo;
import gwt.test.chain.GivenScenarioChain;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

	@Autowired
	private ApplicationContext context;
	
	@Value(value="${local.server.port}")
	protected int port;
	
	@Autowired
	protected OrderRepo orderRepo;
	
	@Autowired
	protected ItemRepo itemRepo;
	
	@AfterEach
	void teardown() {
		orderRepo.deleteAll();
		itemRepo.deleteAll();		
	}
	
	protected GivenScenarioChain given() {
		return new GivenScenarioChain(context);
	}
	
}
