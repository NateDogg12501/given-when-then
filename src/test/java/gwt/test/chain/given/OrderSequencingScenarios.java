package gwt.test.chain.given;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class OrderSequencingScenarios extends GivenScenarioChain {

	private EntityManager entityManager;
	
	private Long nextId;
	
	public OrderSequencingScenarios() {
		super();
		
		entityManager = this.getBean(EntityManagerFactory.class).createEntityManager();
	}
	
	public OrderSequencingScenarios willHaveId(Long nextId) {
		this.nextId = nextId;
		return this;
	}
	
	@Override
	protected GivenScenarioChain closeCurrentChain() {
		EntityTransaction txn = entityManager.getTransaction();
		txn.begin();
		entityManager.createNativeQuery("alter sequence order_id_seq restart with ?").setParameter(1, nextId).executeUpdate();
		txn.commit();
		
		return this;
	}

}
